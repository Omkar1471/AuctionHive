package com.auctionhive.auction_service.utils;

public class ErrorCode {

    /* ===================== COMMON ===================== */

    public static final String NO_DATA_FOUND =
            "No data found against provided request.";

    public static final String INTERNAL_SERVER_ERROR =
            "Internal server error occurred. Please try again later.";

    public static final String UNAUTHORIZED =
            "The person is unauthorized.";

    public static final String ACCESS_DENIED =
            "Access denied.";

    public static final String VALIDATION_ERROR =
            "Validation error.";

    public static final String BAD_REQUEST =
            "Bad request.";

    public static final String BUSINESS_ERROR =
            "Business related error.";

    /* ===================== AUCTION ===================== */

    public static final String AUCTION_NOT_FOUND =
            "Auction not found against provided request.";

    public static final String INVALID_TIME_RANGE =
            "Auction end time must be greater than start time.";

    public static final String AUCTION_ALREADY_ENDED =
            "Auction has already ended.";

    public static final String AUCTION_NOT_LIVE =
            "Auction is not live.";

    public static final String AUCTION_UPDATE_NOT_ALLOWED =
            "Auction cannot be updated at this stage.";

    public static final String SELLER_NOT_ALLOWED =
            "Only seller can perform this operation.";

    /* ===================== BID (FOR NEXT SERVICE) ===================== */

    public static final String INVALID_BID_AMOUNT =
            "Bid amount must be greater than current highest bid.";

    public static final String BID_NOT_ALLOWED =
            "Bidding is not allowed on this auction.";

    public static final String BIDDER_NOT_ALLOWED =
            "Seller cannot place bid on own auction.";
    public static final String INVALID_CREDENTIALS ="Invalid Credentials" ;

	public static final String INVALID_AUCTION_STATE = "Invalid Auction State";

	public static final String LIMIT_EXCEEDED = "Limit Exceeded";

	public static final String INTERNAL_ERROR = "Internal Error";

	public static final String INVALID_REQUEST = "Invalid Request";

    private ErrorCode() {
        // prevent instantiation
    }
}
