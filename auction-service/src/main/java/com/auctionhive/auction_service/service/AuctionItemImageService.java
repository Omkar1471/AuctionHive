package com.auctionhive.auction_service.service;

import com.auctionhive.auction_service.dto.request.AddAuctionItemImageRequest;
import com.auctionhive.auction_service.utils.BaseResponse;

public interface AuctionItemImageService {

    BaseResponse addImage(Long auctionId,
                          AddAuctionItemImageRequest request);

    BaseResponse getAuctionImages(Long auctionId);

    BaseResponse deleteImage(Long imageId);
}
