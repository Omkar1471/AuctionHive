package com.auctionhive.bid_service.controller;

import com.auctionhive.bid_service.service.BidService;
import com.auctionhive.bid_service.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bids")
@RequiredArgsConstructor
public class PublicBidController {

    private final BidService bidService;

    @GetMapping("/auction/{auctionId}/item/{itemId}")
    public ResponseEntity<BaseResponse> getBidHistory(
            @PathVariable Long auctionId,
            @PathVariable Long itemId) {

        BaseResponse response =
                bidService.getBidHistory(auctionId, itemId);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }
}
