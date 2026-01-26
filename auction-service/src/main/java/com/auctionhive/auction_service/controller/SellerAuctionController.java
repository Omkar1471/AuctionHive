package com.auctionhive.auction_service.controller;

import com.auctionhive.auction_service.dto.request.*;
import com.auctionhive.auction_service.service.AuctionService;
import com.auctionhive.auction_service.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller/auctions")
@RequiredArgsConstructor
@Slf4j
public class SellerAuctionController {

    private final AuctionService auctionService;

    /* ================= CREATE AUCTION ================= */
    @PostMapping
    public ResponseEntity<BaseResponse> createAuction(
            @RequestBody CreateAuctionRequest request) {

        log.info(">>> SellerAuctionController.createAuction() HIT");

        BaseResponse response = auctionService.createAuction(request);

        log.info("<<< SellerAuctionController.createAuction() EXIT");

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ================= UPDATE AUCTION ================= */
    @PutMapping("/{auctionId}")
    public ResponseEntity<BaseResponse> updateAuction(
            @PathVariable Long auctionId,
            @RequestBody UpdateAuctionRequest request) {

        log.info(">>> SellerAuctionController.updateAuction() HIT, auctionId={}", auctionId);

        BaseResponse response =
                auctionService.updateAuction(auctionId, request);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ================= GET MY AUCTIONS ================= */
    @GetMapping
    public ResponseEntity<BaseResponse> getMyAuctions() {

        log.info(">>> SellerAuctionController.getMyAuctions() HIT");

        BaseResponse response = auctionService.getMyAuctions();

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ================= GET MY AUCTION DETAILS ================= */
    @GetMapping("/{auctionId}")
    public ResponseEntity<BaseResponse> getMyAuctionDetails(
            @PathVariable Long auctionId) {

        log.info(">>> SellerAuctionController.getMyAuctionDetails() HIT, auctionId={}", auctionId);

        BaseResponse response =
                auctionService.getMyAuctionDetails(auctionId);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }
}
