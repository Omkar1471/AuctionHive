package com.auctionhive.auction_service.exception;

public class AuctionNotFoundException extends RuntimeException {

    public AuctionNotFoundException(String message) {
        super(message);
    }
}
