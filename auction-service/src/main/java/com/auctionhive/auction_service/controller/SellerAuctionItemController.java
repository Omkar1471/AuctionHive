package com.auctionhive.auction_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auctionhive.auction_service.dto.request.CreateAuctionItemRequest;
import com.auctionhive.auction_service.dto.request.UpdateAuctionItemRequest;
import com.auctionhive.auction_service.service.AuctionItemService;
import com.auctionhive.auction_service.utils.BaseResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seller/auctions/{auctionId}/items")
@RequiredArgsConstructor
public class SellerAuctionItemController {

    private final AuctionItemService auctionItemService;

    /* ================= ADD ITEM ================= */

    @PostMapping
    public ResponseEntity<BaseResponse> addItem(
            @PathVariable Long auctionId,
            @RequestBody @Valid CreateAuctionItemRequest request) {

        BaseResponse response =
                auctionItemService.addItem(auctionId, request);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ================= UPDATE ITEM ================= */

    @PutMapping("/{itemId}")
    public ResponseEntity<BaseResponse> updateItem(
            @PathVariable Long auctionId,
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateAuctionItemRequest request) {

        BaseResponse response =
                auctionItemService.updateItem(auctionId, itemId, request);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ================= DELETE ITEM ================= */

    @DeleteMapping("/{itemId}")
    public ResponseEntity<BaseResponse> deleteItem(
            @PathVariable Long auctionId,
            @PathVariable Long itemId) {

        BaseResponse response =
                auctionItemService.deleteItem(auctionId, itemId);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }
}
