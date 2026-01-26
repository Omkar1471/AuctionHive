package com.auctionhive.auction_service.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.auctionhive.auction_service.dto.request.AddAuctionItemImageRequest;
import com.auctionhive.auction_service.entity.Auction;
import com.auctionhive.auction_service.entity.AuctionImage;
import com.auctionhive.auction_service.entity.AuctionStatus;
import com.auctionhive.auction_service.repository.AuctionImageRepository;
import com.auctionhive.auction_service.repository.AuctionRepository;
import com.auctionhive.auction_service.security.RequestContext;
import com.auctionhive.auction_service.service.AuctionItemImageService;
import com.auctionhive.auction_service.utils.BaseResponse;
import com.auctionhive.auction_service.utils.ErrorCode;
import com.auctionhive.auction_service.utils.Errors;
import com.auctionhive.auction_service.utils.GsUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuctionItemImageServiceImpl
        implements AuctionItemImageService {

    private final AuctionRepository auctionRepository;
    private final AuctionImageRepository auctionImageRepository;

    private static final int MAX_IMAGES = 5;

    /* ================= ADD IMAGE ================= */

    @Override
    public BaseResponse addImage(Long auctionId,
                                 AddAuctionItemImageRequest request) {

        if (auctionId == null || request == null) {
            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        Long sellerId = RequestContext.get().getUserId();
        if (sellerId == null) {
            return GsUtils.error(
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.UNAUTHORIZED,
                    Errors.ERROR_TYPE.SECURITY
            );
        }

        Auction auction =
                auctionRepository.findByIdAndSellerId(auctionId, sellerId)
                        .orElse(null);

        if (auction == null) {
            return GsUtils.error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        if (auction.getStatus() != AuctionStatus.DRAFT) {
            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID_AUCTION_STATE,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        if (auctionImageRepository.countByAuctionId(auctionId) >= MAX_IMAGES) {
            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.LIMIT_EXCEEDED,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        // Handle primary image logic
        if (request.getPrimaryImage()) {
            List<AuctionImage> existing =
                    auctionImageRepository.findByAuctionId(auctionId);

            existing.forEach(img -> img.setPrimaryImage(false));
            auctionImageRepository.saveAll(existing);
        }

        AuctionImage image = new AuctionImage();
        image.setAuction(auction);
        image.setImageUrl(request.getImageUrl());
        image.setPrimaryImage(request.getPrimaryImage());
        image.setDisplayOrder(request.getDisplayOrder());

        auctionImageRepository.save(image);

        return GsUtils.getBaseResponse(
                HttpStatus.CREATED,
                "Auction image added successfully"
        );
    }

   

    /* ================= DELETE IMAGE ================= */

    @Override
    public BaseResponse deleteImage(Long imageId) {

        Long sellerId = RequestContext.get().getUserId();
        if (sellerId == null) {
            return GsUtils.error(
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.UNAUTHORIZED,
                    Errors.ERROR_TYPE.SECURITY
            );
        }

        AuctionImage image =
                auctionImageRepository.findById(imageId).orElse(null);

        if (image == null) {
            return GsUtils.error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        Auction auction = image.getAuction();

        if (!auction.getSellerId().equals(sellerId)
                || auction.getStatus() != AuctionStatus.DRAFT) {

            return GsUtils.error(
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.UNAUTHORIZED,
                    Errors.ERROR_TYPE.SECURITY
            );
        }

        auctionImageRepository.delete(image);

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Auction image deleted successfully"
        );
    }

	@Override
	public BaseResponse getAuctionImages(Long auctionId) {
		 if (auctionId == null) {
	            return GsUtils.error(
	                    HttpStatus.BAD_REQUEST,
	                    ErrorCode.NO_DATA_FOUND,
	                    Errors.ERROR_TYPE.AUCTION
	            );
	        }

	        return GsUtils.getBaseResponse(
	                HttpStatus.OK,
	                auctionImageRepository.findByAuctionId(auctionId)
	        );
	}

	
}
