package com.auctionhive.user_service.controller;

import com.auctionhive.user_service.dto.request.*;
import com.auctionhive.user_service.service.UserService;
import com.auctionhive.user_service.utils.BaseResponse;
import com.auctionhive.user_service.utils.EndPointReffer;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final  UserService userService;



    /* ===================== REGISTER ===================== */

    @PostMapping(EndPointReffer.USER_REGISTER)
    public ResponseEntity<BaseResponse> register(
            @RequestBody @Valid UserRegisterDTO request) {

        BaseResponse response = userService.add(request);
        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ===================== LOGIN ===================== */

    @PostMapping(EndPointReffer.USER_LOGIN)
    @Operation(description = "User Authentication With Spring Security")
    public ResponseEntity<BaseResponse> login(
            @RequestBody @Valid UserSignInDTO request) {

        return ResponseEntity.ok(userService.userSignIn(request));
    }

    /* ===================== GET MY PROFILE ===================== */

    @GetMapping("/me")
    @Operation(description = "Get logged-in user profile")
    public ResponseEntity<BaseResponse> getMyProfile() {

        BaseResponse response = userService.getMyProfile();
        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ===================== UPDATE MY PROFILE ===================== */

    @PutMapping("/me")
    @Operation(description = "Update logged-in user profile")
    public ResponseEntity<BaseResponse> updateMyProfile(
            @RequestBody @Valid UserUpdateDTO request) {

        BaseResponse response = userService.updateMyProfile(request);
        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ===================== CHANGE PASSWORD ===================== */

    @PutMapping("/change-password")
    @Operation(description = "Change password for logged-in user")
    public ResponseEntity<BaseResponse> changePassword(
            @RequestBody @Valid ChangePasswordDTO request) {

        BaseResponse response = userService.changePassword(request);
        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }



}
