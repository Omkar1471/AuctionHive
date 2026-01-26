package com.auctionhive.user_service.service.impl;

import com.auctionhive.user_service.dto.request.ChangePasswordDTO;
import com.auctionhive.user_service.dto.request.UserUpdateDTO;
import com.auctionhive.user_service.dto.response.UserDetailsDTO;
import com.auctionhive.user_service.entity.Role;
import com.auctionhive.user_service.entity.Status;
import com.auctionhive.user_service.entity.User;
import com.auctionhive.user_service.repository.UserRepo;
import com.auctionhive.user_service.security.RequestContext;
import com.auctionhive.user_service.service.SellerService;
import com.auctionhive.user_service.utils.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.auctionhive.user_service.utils.GsUtils.error;

@Service
@Transactional
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private static final Logger log =
            LoggerFactory.getLogger(SellerServiceImpl.class);

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    /* ===================== PROFILE ===================== */

    @Override
    public BaseResponse getMyProfile() {

        Optional<User> optionalUser = getCurrentSeller();

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

    @Override
    public BaseResponse updateMyProfile(UserUpdateDTO dto) {

        Optional<User> optionalUser = getCurrentSeller();

        if (optionalUser.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        User seller = optionalUser.get();
        seller.setName(dto.getName());
        seller.setPhone(dto.getPhone());

        UserDetailsDTO response =
                modelMapper.map(seller, UserDetailsDTO.class);

        return GsUtils.getBaseResponse(HttpStatus.OK, response);
    }

    /* ===================== PASSWORD ===================== */

    @Override
    public BaseResponse changePassword(ChangePasswordDTO dto) {

        Optional<User> optionalUser = getCurrentSeller();

        if (optionalUser.isEmpty()) {
            return error(
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.UNAUTHORIZED,
                    Errors.ERROR_TYPE.SECURITY
            );
        }

        User seller = optionalUser.get();

        if (!passwordEncoder.matches(
                dto.getCurrentPassword(),
                seller.getPassword())) {

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

        seller.setPassword(
                passwordEncoder.encode(dto.getNewPassword())
        );

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Password changed successfully"
        );
    }

    /* ===================== STATUS ===================== */

    @Override
    public BaseResponse getSellerStatus() {

        Optional<User> optionalUser = getCurrentSeller();

        if (optionalUser.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                optionalUser.get().getStatus()
        );
    }

    @Override
    public BaseResponse deactivateAccount() {

        Optional<User> optionalUser = getCurrentSeller();

        if (optionalUser.isEmpty()) {
            return error(
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.UNAUTHORIZED,
                    Errors.ERROR_TYPE.SECURITY
            );
        }

        User seller = optionalUser.get();
        seller.setStatus(Status.BLOCKED);

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Seller account deactivated"
        );
    }

    /* ===================== INTER-SERVICE ===================== */

    @Override
    public BaseResponse isSellerActive(Long sellerId) {

        Optional<User> userOpt = userRepo.findById(sellerId);

        if (userOpt.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        User seller = userOpt.get();

        if (seller.getRole() != Role.SELLER) {
            return error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID_ROLE,
                    Errors.ERROR_TYPE.USER
            );
        }

        boolean isActive =
                seller.getStatus() == Status.ACTIVE;

        return GsUtils.getBaseResponse(HttpStatus.OK, isActive);
    }

    /* ===================== INTERNAL ===================== */

    private Optional<User> getCurrentSeller() {
        try {
            RequestContext ctx = RequestContext.get();

            if (!"SELLER".equals(ctx.getRole())) {
                return Optional.empty();
            }

            return userRepo.findByEmail(ctx.getEmail());
        } catch (Exception e) {
            log.error("Failed to resolve seller", e);
            return Optional.empty();
        }
    }

}
