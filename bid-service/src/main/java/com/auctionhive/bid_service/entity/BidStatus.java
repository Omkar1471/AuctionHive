package com.auctionhive.bid_service.entity;

public enum BidStatus {

    ACTIVE,        // Current valid bid
    OUTBID,        // Replaced by higher bid
    WON,           // Winning bid after auction end
    LOST,          // Bid lost
    CANCELLED      // Manual/system cancel
}
