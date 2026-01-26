package com.auctionhive.bid_service.controller;

import com.auctionhive.bid_service.dto.request.PlaceBidRequest;
import com.auctionhive.bid_service.service.BidService;
import com.auctionhive.bid_service.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer/bids")
@RequiredArgsConstructor
public class BuyerBidController {

    private final BidService bidService;

    @PostMapping
    public ResponseEntity<BaseResponse> placeBid(
            @RequestBody PlaceBidRequest request) {

        BaseResponse response = bidService.placeBid(request);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    @GetMapping("/my")
    public ResponseEntity<BaseResponse> getMyBids() {

        BaseResponse response = bidService.getMyBids();

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }
}
