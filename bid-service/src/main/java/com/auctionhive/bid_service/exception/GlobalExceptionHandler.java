package com.auctionhive.bid_service.exception;

import com.auctionhive.bid_service.utils.BaseResponse;
import com.auctionhive.bid_service.utils.ErrorCode;
import com.auctionhive.bid_service.utils.Errors;
import com.auctionhive.bid_service.utils.GsUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* ================= GENERIC RUNTIME ================= */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse handleRuntime(RuntimeException ex) {

        return GsUtils.error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR,
                Errors.ERROR_TYPE.BID

        );
    }

    /* ================= ILLEGAL STATE ================= */
    @ExceptionHandler(IllegalStateException.class)
    public BaseResponse handleIllegalState(IllegalStateException ex) {

        return GsUtils.error(
                HttpStatus.BAD_REQUEST,
                ErrorCode.INVALID_STATE,
                Errors.ERROR_TYPE.BID
        );
    }

    /* ================= FALLBACK ================= */
    @ExceptionHandler(Exception.class)
    public BaseResponse handleException(Exception ex) {

        return GsUtils.error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR,
                Errors.ERROR_TYPE.BID
        );
    }
}
