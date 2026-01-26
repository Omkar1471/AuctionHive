package com.auctionhive.bid_service.service;

import com.auctionhive.bid_service.dto.request.PlaceBidRequest;
import com.auctionhive.bid_service.utils.BaseResponse;

public interface BidService {

    BaseResponse placeBid(PlaceBidRequest request);

    BaseResponse getMyBids();

    BaseResponse getBidHistory(Long auctionId, Long itemId);
}
