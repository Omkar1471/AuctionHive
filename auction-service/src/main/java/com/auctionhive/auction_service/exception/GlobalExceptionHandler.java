package com.auctionhive.auction_service.exception;

import com.auctionhive.auction_service.utils.BaseResponse;
import com.auctionhive.auction_service.utils.ErrorCode;
import com.auctionhive.auction_service.utils.Errors;
import com.auctionhive.auction_service.utils.GsUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuctionNotFoundException.class)
    public BaseResponse handleAuctionNotFound(AuctionNotFoundException ex) {

        return GsUtils.error(
                HttpStatus.NOT_FOUND,
                ErrorCode.NO_DATA_FOUND,
                Errors.ERROR_TYPE.AUCTION
        );
    }

    @ExceptionHandler(InvalidAuctionStateException.class)
    public BaseResponse handleInvalidState(InvalidAuctionStateException ex) {

        return GsUtils.error(
                HttpStatus.BAD_REQUEST,
                ErrorCode.INVALID_AUCTION_STATE,
                Errors.ERROR_TYPE.AUCTION
        );
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse handleGeneric(Exception ex) {

        return GsUtils.error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR,
                Errors.ERROR_TYPE.SYSTEM
        );
    }
}
