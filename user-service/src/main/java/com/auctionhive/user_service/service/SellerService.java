package com.auctionhive.user_service.service;

import com.auctionhive.user_service.dto.request.ChangePasswordDTO;
import com.auctionhive.user_service.dto.request.UserUpdateDTO;
import com.auctionhive.user_service.utils.BaseResponse;

public interface SellerService {

    BaseResponse getMyProfile();

    BaseResponse updateMyProfile(UserUpdateDTO dto);

    BaseResponse changePassword(ChangePasswordDTO dto);

    BaseResponse getSellerStatus();

    BaseResponse deactivateAccount();

    BaseResponse isSellerActive(Long sellerId);
}
