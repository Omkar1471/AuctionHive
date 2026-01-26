package com.auctionhive.user_service.controller;

import com.auctionhive.user_service.dto.request.ChangePasswordDTO;
import com.auctionhive.user_service.dto.request.UserUpdateDTO;
import com.auctionhive.user_service.service.SellerService;
import com.auctionhive.user_service.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SELLER')")
public class SellerController {

    private final SellerService sellerService;

    /* ===================== PROFILE ===================== */

    // 1️⃣ Get seller profile
    @GetMapping("/profile")
    public ResponseEntity<BaseResponse> getMyProfile() {

        BaseResponse response =
                sellerService.getMyProfile();

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    // 2️⃣ Update seller profile
    @PutMapping("/profile")
    public ResponseEntity<BaseResponse> updateMyProfile(
            @RequestBody UserUpdateDTO dto) {

        BaseResponse response =
                sellerService.updateMyProfile(dto);

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
                sellerService.changePassword(dto);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ===================== SELLER STATUS ===================== */

    // 4️⃣ Get seller approval / account status
    @GetMapping("/status")
    public ResponseEntity<BaseResponse> getSellerStatus() {

        BaseResponse response =
                sellerService.getSellerStatus();

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    // 5️⃣ Deactivate seller account
    @PutMapping("/deactivate")
    public ResponseEntity<BaseResponse> deactivateAccount() {

        BaseResponse response =
                sellerService.deactivateAccount();

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ===================== INTER-SERVICE ===================== */

    // 6️⃣ Check if seller is active (used by Auction Service)
    @GetMapping("/{sellerId}/is-active")
    @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<BaseResponse> isSellerActive(
            @PathVariable Long sellerId) {

        BaseResponse response =
                sellerService.isSellerActive(sellerId);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }
}
