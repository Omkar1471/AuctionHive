package com.auctionhive.bid_service.utils;


public enum CustomHttpStatus {


    SUCCESS(200, "Success"),
    FAILURE(201, "Failure");


    private final int value;
    private final String reasonPhrase;

    CustomHttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }


}
