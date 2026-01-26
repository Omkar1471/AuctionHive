package com.auctionhive.user_service.service;

import com.auctionhive.user_service.dto.request.UserStatusRequest;
import com.auctionhive.user_service.utils.BaseResponse;

public interface AdminService {

    /* ===================== USER ===================== */

    BaseResponse getAllUsers();

    BaseResponse getUserById(Long userId);

    BaseResponse updateUserStatus(Long userId, UserStatusRequest request);

    BaseResponse deleteUser(Long userId);

    /* ===================== SELLER ===================== */

    BaseResponse getAllSellers();

    BaseResponse approveSeller(Long sellerId);

    BaseResponse rejectSeller(Long sellerId);

    BaseResponse blockSeller(Long sellerId);

    /* ===================== BUYER ===================== */

    BaseResponse getAllBuyers();

    BaseResponse updateBuyerStatus(Long buyerId, UserStatusRequest request);

    /* ===================== STATS ===================== */

    BaseResponse getUserStatistics();
}
