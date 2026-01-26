package com.auctionhive.auction_service.utils;

public final class EndPointReffer {

    private EndPointReffer() {
        // prevent instantiation
    }

    /* ===================== AUCTION ===================== */

    public static final String CREATE_AUCTION = "/create";

    public static final String GET_AUCTION_BY_ID = "/{auctionId}";

    public static final String GET_MY_AUCTIONS = "/my";

    public static final String UPDATE_AUCTION = "/{auctionId}";

    public static final String CLOSE_AUCTION = "/{auctionId}/close";
}
