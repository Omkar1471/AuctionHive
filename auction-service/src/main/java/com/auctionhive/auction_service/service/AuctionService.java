package com.auctionhive.auction_service.service;

import com.auctionhive.auction_service.dto.request.*;
import com.auctionhive.auction_service.utils.BaseResponse;

public interface AuctionService {

    /* ================= SELLER ================= */

    BaseResponse createAuction(CreateAuctionRequest request);

    BaseResponse updateAuction(Long auctionId,
                               UpdateAuctionRequest request);

    BaseResponse getMyAuctions();

    BaseResponse getMyAuctionDetails(Long auctionId);


    /* ================= ADMIN ================= */

    BaseResponse updateAuctionStatus(Long auctionId,
                                     AuctionStatusUpdateRequest request);

    BaseResponse getAllAuctionsForAdmin();


    /* ================= PUBLIC ================= */

    BaseResponse getPublicAuctions();

    BaseResponse getAuctionDetails(Long auctionId);
}
