package com.auctionhive.user_service.service;

import com.auctionhive.user_service.dto.request.ChangePasswordDTO;
import com.auctionhive.user_service.dto.request.UserUpdateDTO;
import com.auctionhive.user_service.utils.BaseResponse;

public interface BuyerService {

    /* ===================== PROFILE ===================== */

    BaseResponse getMyProfile();

    BaseResponse updateMyProfile(UserUpdateDTO dto);

    /* ===================== PASSWORD ===================== */

    BaseResponse changePassword(ChangePasswordDTO dto);

    /* ===================== ACCOUNT ===================== */

    BaseResponse deactivateAccount();

    /* ===================== INTER-SERVICE ===================== */

    BaseResponse isBuyerActive(Long buyerId);
}
