package com.auctionhive.user_service.service.impl;

import com.auctionhive.user_service.dto.request.UserStatusRequest;
import com.auctionhive.user_service.entity.Role;
import com.auctionhive.user_service.entity.Status;
import com.auctionhive.user_service.entity.User;
import com.auctionhive.user_service.repository.UserRepo;
import com.auctionhive.user_service.service.AdminService;
import com.auctionhive.user_service.utils.BaseResponse;
import com.auctionhive.user_service.utils.ErrorCode;
import com.auctionhive.user_service.utils.Errors;
import com.auctionhive.user_service.utils.GsUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.auctionhive.user_service.utils.GsUtils.error;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepo userRepo;

    /* ===================== USER ===================== */

    @Override
    public BaseResponse getAllUsers() {

        List<User> users = userRepo.findAll();

        if (users.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        return GsUtils.getBaseResponse(HttpStatus.OK, users);
    }

    @Override
    public BaseResponse getUserById(Long userId) {

        Optional<User> userOpt = userRepo.findById(userId);

        if (userOpt.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        return GsUtils.getBaseResponse(HttpStatus.OK, userOpt.get());
    }

    @Override
    public BaseResponse updateUserStatus(
            Long userId,
            UserStatusRequest request) {

        Optional<User> userOpt = userRepo.findById(userId);

        if (userOpt.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        User user = userOpt.get();
        user.setStatus(request.getStatus());

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "User status updated successfully"
        );
    }

    @Override
    public BaseResponse deleteUser(Long userId) {

        Optional<User> userOpt = userRepo.findById(userId);

        if (userOpt.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        User user = userOpt.get();
        user.setStatus(Status.DELETED); // soft delete

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "User deleted successfully"
        );
    }

    /* ===================== SELLER ===================== */

    @Override
    public BaseResponse getAllSellers() {

        List<User> sellers =
                userRepo.findByRole(Role.SELLER);

        if (sellers.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        return GsUtils.getBaseResponse(HttpStatus.OK, sellers);
    }

    @Override
    public BaseResponse approveSeller(Long sellerId) {

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

        seller.setStatus(Status.ACTIVE);

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Seller approved successfully"
        );
    }

    @Override
    public BaseResponse rejectSeller(Long sellerId) {

        Optional<User> userOpt = userRepo.findById(sellerId);

        if (userOpt.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        User seller = userOpt.get();
        seller.setStatus(Status.REJECTED);

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Seller rejected successfully"
        );
    }

    @Override
    public BaseResponse blockSeller(Long sellerId) {

        Optional<User> userOpt = userRepo.findById(sellerId);

        if (userOpt.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        User seller = userOpt.get();
        seller.setStatus(Status.BLOCKED);

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Seller blocked successfully"
        );
    }

    /* ===================== BUYER ===================== */

    @Override
    public BaseResponse getAllBuyers() {

        List<User> buyers =
                userRepo.findByRole(Role.BUYER);

        if (buyers.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        return GsUtils.getBaseResponse(HttpStatus.OK, buyers);
    }

    @Override
    public BaseResponse updateBuyerStatus(
            Long buyerId,
            UserStatusRequest request) {

        Optional<User> userOpt = userRepo.findById(buyerId);

        if (userOpt.isEmpty()) {
            return error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.USER
            );
        }

        User buyer = userOpt.get();
        buyer.setStatus(request.getStatus());

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Buyer status updated successfully"
        );
    }

    /* ===================== STATS ===================== */

    @Override
    public BaseResponse getUserStatistics() {

        long total = userRepo.count();
        long active = userRepo.countByStatus(Status.ACTIVE);
        long blocked = userRepo.countByStatus(Status.BLOCKED);
        long sellers = userRepo.countByRole(Role.SELLER);

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                new Object() {
                    public final long totalUsers = total;
                    public final long activeUsers = active;
                    public final long blockedUsers = blocked;
                    public final long sellerCount = sellers;
                }
        );
    }
}
