package com.auctionhive.user_service.service;

import com.auctionhive.user_service.dto.request.*;
import com.auctionhive.user_service.utils.BaseResponse;
import jakarta.validation.Valid;

public interface UserService {
    BaseResponse add(UserRegisterDTO user);



    BaseResponse userSignIn(@Valid UserSignInDTO request);


    BaseResponse getMyProfile();

    BaseResponse updateMyProfile(@Valid UserUpdateDTO request);

    BaseResponse changePassword(@Valid ChangePasswordDTO request);




}
