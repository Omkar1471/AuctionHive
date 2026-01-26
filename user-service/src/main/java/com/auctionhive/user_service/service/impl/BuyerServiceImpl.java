package com.auctionhive.user_service.service.impl;

import com.auctionhive.user_service.dto.request.ChangePasswordDTO;
import com.auctionhive.user_service.dto.request.UserUpdateDTO;
import com.auctionhive.user_service.dto.response.UserDetailsDTO;
import com.auctionhive.user_service.entity.Role;
import com.auctionhive.user_service.entity.Status;
import com.auctionhive.user_service.entity.User;
import com.auctionhive.user_service.repository.UserRepo;
import com.auctionhive.user_service.security.RequestContext;
import com.auctionhive.user_service.service.BuyerService;
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
public class BuyerServiceImpl implements BuyerService {

    private static final Logger log =
            LoggerFactory.getLogger(BuyerServiceImpl.class);

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    /* ===================== PROFILE ===================== */

    @Override
    public BaseResponse getMyProfile() {

        Optional<User> optionalUser = getCurrentBuyer();

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

        Optional<User> optionalUser = getCurrentBuyer();

        if (optionalUser.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        User buyer = optionalUser.get();
        buyer.setName(dto.getName());
        buyer.setPhone(dto.getPhone());

        UserDetailsDTO response =
                modelMapper.map(buyer, UserDetailsDTO.class);

        return GsUtils.getBaseResponse(HttpStatus.OK, response);
    }

    /* ===================== PASSWORD ===================== */

    @Override
    public BaseResponse changePassword(ChangePasswordDTO dto) {

        Optional<User> optionalUser = getCurrentBuyer();

        if (optionalUser.isEmpty()) {
            return error(
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.UNAUTHORIZED,
                    Errors.ERROR_TYPE.SECURITY
            );
        }

        User buyer = optionalUser.get();

        if (!passwordEncoder.matches(
                dto.getCurrentPassword(),
                buyer.getPassword())) {

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

        buyer.setPassword(
                passwordEncoder.encode(dto.getNewPassword())
        );

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Password changed successfully"
        );
    }

    /* ===================== ACCOUNT ===================== */

    @Override
    public BaseResponse deactivateAccount() {

        Optional<User> optionalUser = getCurrentBuyer();

        if (optionalUser.isEmpty()) {
            return error(
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.UNAUTHORIZED,
                    Errors.ERROR_TYPE.SECURITY
            );
        }

        User buyer = optionalUser.get();
        buyer.setStatus(Status.BLOCKED);

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Buyer account deactivated"
        );
    }

    /* ===================== INTER-SERVICE ===================== */

    @Override
    public BaseResponse isBuyerActive(Long buyerId) {

        Optional<User> userOpt = userRepo.findById(buyerId);

        if (userOpt.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        User buyer = userOpt.get();

        if (buyer.getRole() != Role.BUYER) {
            return error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID_ROLE,
                    Errors.ERROR_TYPE.USER
            );
        }

        boolean isActive =
                buyer.getStatus() == Status.ACTIVE;

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                isActive
        );
    }

    /* ===================== INTERNAL ===================== */

    private Optional<User> getCurrentBuyer() {
        try {
            RequestContext ctx = RequestContext.get();

            if (!"BUYER".equals(ctx.getRole())) {
                return Optional.empty();
            }

            return userRepo.findByEmail(ctx.getEmail());
        } catch (Exception e) {
            log.error("Failed to resolve buyer", e);
            return Optional.empty();
        }
    }

}
