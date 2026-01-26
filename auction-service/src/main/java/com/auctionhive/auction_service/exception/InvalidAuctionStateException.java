package com.auctionhive.auction_service.exception;

public class InvalidAuctionStateException extends RuntimeException {

    public InvalidAuctionStateException(String message) {
        super(message);
    }
}
