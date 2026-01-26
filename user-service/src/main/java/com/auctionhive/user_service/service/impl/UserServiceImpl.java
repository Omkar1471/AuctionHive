package com.auctionhive.user_service.service.impl;


import com.auctionhive.user_service.dto.request.*;
import com.auctionhive.user_service.dto.response.UserDetailsDTO;
import com.auctionhive.user_service.entity.Status;
import com.auctionhive.user_service.entity.User;
import com.auctionhive.user_service.repository.UserRepo;
import com.auctionhive.user_service.security.JwtUtils;
import com.auctionhive.user_service.security.RequestContext;
import com.auctionhive.user_service.security.UserPrincipal;
import com.auctionhive.user_service.service.UserService;
import com.auctionhive.user_service.utils.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static com.auctionhive.user_service.utils.GsUtils.error;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log =
            LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
private final JwtUtils jwtUtils;
    /* ===================== REGISTER ===================== */

    @Override
    public BaseResponse add(UserRegisterDTO dto) {

        if (dto == null) {
            return error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        if (userRepo.existsByEmail(dto.getEmail())) {
            return error(
                    HttpStatus.CONFLICT,
                    ErrorCode.EMAIL_ALREADY_EXISTS,
                    Errors.ERROR_TYPE.USER
            );
        }

        User user = modelMapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setStatus(Status.ACTIVE);

        userRepo.save(user);

        return GsUtils.getBaseResponse(
                HttpStatus.CREATED,
                "User registered successfully"
        );
    }

    /* ===================== LOGIN ===================== */

    @Override
    public BaseResponse userSignIn(UserSignInDTO dto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                dto.getEmail(),
                                dto.getPassword()
                        )
                );

        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();

        String token = jwtUtils.generateToken(
                principal.getUserId(),
                principal.getUsername(),
                principal.getUserRole()
        );

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                Map.of(
                        "token", token,
                        "type", "Bearer"
                )
        );
    }


    /* ===================== GET MY PROFILE ===================== */

    @Override
    public BaseResponse getMyProfile() {

        Optional<User> optionalUser = getCurrentUser();

        if (optionalUser.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        UserDetailsDTO dto =
                modelMapper.map(optionalUser.get(), UserDetailsDTO.class);

        return GsUtils.getBaseResponse(HttpStatus.OK, dto);
    }

    /* ===================== UPDATE MY PROFILE ===================== */

    @Override
    public BaseResponse updateMyProfile(UserUpdateDTO dto) {

        Optional<User> optionalUser = getCurrentUser();

        if (optionalUser.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        User user = optionalUser.get();
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());

        UserDetailsDTO response =
                modelMapper.map(user, UserDetailsDTO.class);

        return GsUtils.getBaseResponse(HttpStatus.OK, response);
    }

    /* ===================== CHANGE PASSWORD ===================== */

    @Override
    public BaseResponse changePassword(ChangePasswordDTO dto) {

        Optional<User> optionalUser = getCurrentUser();

        if (optionalUser.isEmpty()) {
            return error(
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.UNAUTHORIZED,
                    Errors.ERROR_TYPE.SECURITY
            );
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(
                dto.getCurrentPassword(),
                user.getPassword())) {

            return error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID_PASSWORD,
                    Errors.ERROR_TYPE.USER
            );
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            return error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.PASSWORD_MISMATCH,
                    Errors.ERROR_TYPE.USER
            );
        }

        user.setPassword(
                passwordEncoder.encode(dto.getNewPassword())
        );

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Password changed successfully"
        );
    }

    /* ===================== INTERNAL ===================== */

    private Optional<User> getCurrentUser() {
        try {
            Long userId = RequestContext.get().getUserId();
            if (userId == null) {
                return Optional.empty();
            }
            return userRepo.findById(userId);
        } catch (Exception e) {
            log.error("Failed to resolve current user", e);
            return Optional.empty();
        }
    }
}
