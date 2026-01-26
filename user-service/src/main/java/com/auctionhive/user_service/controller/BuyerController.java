package com.auctionhive.user_service.controller;

import com.auctionhive.user_service.dto.request.ChangePasswordDTO;
import com.auctionhive.user_service.dto.request.UserUpdateDTO;
import com.auctionhive.user_service.service.BuyerService;
import com.auctionhive.user_service.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer")
@RequiredArgsConstructor
@PreAuthorize("hasRole('BUYER')")
public class BuyerController {

    private final BuyerService buyerService;

    /* ===================== PROFILE ===================== */

    // 1️⃣ Get buyer profile
    @GetMapping("/profile")
    public ResponseEntity<BaseResponse> getMyProfile() {

        BaseResponse response =
                buyerService.getMyProfile();

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    // 2️⃣ Update buyer profile
    @PutMapping("/profile")
    public ResponseEntity<BaseResponse> updateMyProfile(
            @RequestBody UserUpdateDTO dto) {

        BaseResponse response =
                buyerService.updateMyProfile(dto);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ===================== PASSWORD ===================== */

    // 3️⃣ Change password
    @PutMapping("/change-password")
    public ResponseEntity<BaseResponse> changePassword(
            @RequestBody ChangePasswordDTO dto) {

        BaseResponse response =
                buyerService.changePassword(dto);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ===================== ACCOUNT ===================== */

    // 4️⃣ Deactivate buyer account
    @PutMapping("/deactivate")
    public ResponseEntity<BaseResponse> deactivateAccount() {

        BaseResponse response =
                buyerService.deactivateAccount();

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ===================== INTER-SERVICE ===================== */

    // 5️⃣ Check if buyer is active (used by Bid / Auction service)
    @GetMapping("/{buyerId}/is-active")
    @PreAuthorize("hasAnyRole('ADMIN','BUYER')")
    public ResponseEntity<BaseResponse> isBuyerActive(
            @PathVariable Long buyerId) {

        BaseResponse response =
                buyerService.isBuyerActive(buyerId);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }
}
