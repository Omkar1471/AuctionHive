package com.auctionhive.auction_service.controller;

import com.auctionhive.auction_service.dto.request.AuctionStatusUpdateRequest;
import com.auctionhive.auction_service.service.AuctionService;
import com.auctionhive.auction_service.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auctions")
@RequiredArgsConstructor
public class AdminAuctionController {

    private final AuctionService auctionService;

    /* ================= APPROVE / REJECT / CLOSE ================= */
    @PutMapping("/{auctionId}/status")
    public ResponseEntity<BaseResponse> updateAuctionStatus(
            @PathVariable Long auctionId,
            @RequestBody AuctionStatusUpdateRequest request) {

        BaseResponse response =
                auctionService.updateAuctionStatus(auctionId, request);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ================= GET ALL AUCTIONS ================= */
    @GetMapping
    public ResponseEntity<BaseResponse> getAllAuctions() {

        BaseResponse response =
                auctionService.getAllAuctionsForAdmin();

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }
}
