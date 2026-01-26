package com.auctionhive.user_service.controller;

import com.auctionhive.user_service.dto.request.UserStatusRequest;
import com.auctionhive.user_service.service.AdminService;
import com.auctionhive.user_service.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    /* ===================== USER MANAGEMENT ===================== */

    // 1️⃣ Get all users
    @GetMapping("/users")
    public ResponseEntity<BaseResponse> getAllUsers() {
        BaseResponse response = adminService.getAllUsers();
        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    // 2️⃣ Get user by ID
    @GetMapping("/users/{userId}")
    public ResponseEntity<BaseResponse> getUserById(
            @PathVariable Long userId) {

        BaseResponse response = adminService.getUserById(userId);
        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    // 3️⃣ Enable / Disable user
    @PutMapping("/users/{userId}/status")
    public ResponseEntity<BaseResponse> updateUserStatus(
            @PathVariable Long userId,
            @RequestBody UserStatusRequest request) {

        BaseResponse response =
                adminService.updateUserStatus(userId, request);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    // 4️⃣ Delete user (soft delete)
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<BaseResponse> deleteUser(
            @PathVariable Long userId) {

        BaseResponse response =
                adminService.deleteUser(userId);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ===================== SELLER MANAGEMENT ===================== */

    // 5️⃣ Get all sellers
    @GetMapping("/sellers")
    public ResponseEntity<BaseResponse> getAllSellers() {
        BaseResponse response = adminService.getAllSellers();
        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    // 6️⃣ Approve seller
    @PutMapping("/sellers/{sellerId}/approve")
    public ResponseEntity<BaseResponse> approveSeller(
            @PathVariable Long sellerId) {

        BaseResponse response =
                adminService.approveSeller(sellerId);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    // 7️⃣ Reject seller
    @PutMapping("/sellers/{sellerId}/reject")
    public ResponseEntity<BaseResponse> rejectSeller(
            @PathVariable Long sellerId) {

        BaseResponse response =
                adminService.rejectSeller(sellerId);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    // 8️⃣ Block seller
    @PutMapping("/sellers/{sellerId}/block")
    public ResponseEntity<BaseResponse> blockSeller(
            @PathVariable Long sellerId) {

        BaseResponse response =
                adminService.blockSeller(sellerId);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ===================== BUYER MANAGEMENT ===================== */

    // 9️⃣ Get all buyers
    @GetMapping("/buyers")
    public ResponseEntity<BaseResponse> getAllBuyers() {
        BaseResponse response = adminService.getAllBuyers();
        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    // 🔟 Enable / Disable buyer
    @PutMapping("/buyers/{buyerId}/status")
    public ResponseEntity<BaseResponse> updateBuyerStatus(
            @PathVariable Long buyerId,
            @RequestBody UserStatusRequest request) {

        BaseResponse response =
                adminService.updateBuyerStatus(buyerId, request);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ===================== DASHBOARD / STATS ===================== */

    // 1️⃣1️⃣ User statistics
    @GetMapping("/stats/users")
    public ResponseEntity<BaseResponse> getUserStatistics() {
        BaseResponse response =
                adminService.getUserStatistics();

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }
}
