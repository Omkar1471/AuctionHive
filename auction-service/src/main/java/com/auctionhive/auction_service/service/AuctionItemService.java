package com.auctionhive.auction_service.service;

import com.auctionhive.auction_service.dto.request.CreateAuctionItemRequest;
import com.auctionhive.auction_service.dto.request.UpdateAuctionItemRequest;
import com.auctionhive.auction_service.utils.BaseResponse;

public interface AuctionItemService {

    BaseResponse addItem(Long auctionId, CreateAuctionItemRequest request);

    BaseResponse updateItem(Long auctionId,
                            Long itemId,
                            UpdateAuctionItemRequest request);

    BaseResponse deleteItem(Long auctionId, Long itemId);

    BaseResponse getItemsByAuction(Long auctionId);
}
