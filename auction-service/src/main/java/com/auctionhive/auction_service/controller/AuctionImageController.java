package com.auctionhive.auction_service.controller;

import com.auctionhive.auction_service.dto.request.AddAuctionItemImageRequest;
import com.auctionhive.auction_service.service.AuctionItemImageService;
import com.auctionhive.auction_service.utils.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auctions/{auctionId}/images")
@RequiredArgsConstructor
public class AuctionImageController {

    private final AuctionItemImageService imageService;

    /* ================= ADD IMAGE (SELLER) ================= */

    @PostMapping
    public ResponseEntity<BaseResponse> addImage(
            @PathVariable Long auctionId,
            @RequestBody @Valid AddAuctionItemImageRequest request) {

        BaseResponse response =
                imageService.addImage(auctionId, request);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ================= GET IMAGES (PUBLIC) ================= */

    @GetMapping
    public ResponseEntity<BaseResponse> getAuctionImages(
            @PathVariable Long auctionId) {

        BaseResponse response =
                imageService.getAuctionImages(auctionId);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }

    /* ================= DELETE IMAGE (SELLER) ================= */

    @DeleteMapping("/{imageId}")
    public ResponseEntity<BaseResponse> deleteImage(
            @PathVariable Long auctionId,   // kept for REST clarity
            @PathVariable Long imageId) {

        BaseResponse response =
                imageService.deleteImage(imageId);

        return ResponseEntity
                .status(response.getStatus().getStatusCode())
                .body(response);
    }
}
