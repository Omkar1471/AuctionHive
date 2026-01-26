package com.auctionhive.auction_service.controller;

import com.auctionhive.auction_service.service.AuctionService;
import com.auctionhive.auction_service.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
public class PublicAuctionController {

    private final AuctionService auctionService;

    /* ================= LIVE / UPCOMING AUCTIONS ================= */
    @GetMapping
    public ResponseEntity<BaseResponse> getPublicAuctions() {

        BaseResponse response =
                auctionService.getPublicAuctions();

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ================= AUCTION DETAILS ================= */
    @GetMapping("/{auctionId}")
    public ResponseEntity<BaseResponse> getAuctionDetails(
            @PathVariable Long auctionId) {

        BaseResponse response =
                auctionService.getAuctionDetails(auctionId);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }
}
