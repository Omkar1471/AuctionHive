package com.auctionhive.auction_service.utils;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.http.HttpStatus;


import java.util.*;

@Data
public class GsUtils {

    private static final Logger logger = LoggerFactory.getLogger(GsUtils.class);




    public static BaseResponse getBaseResponse(HttpStatus httpStatus, Object buzResponse) {
        if (null == buzResponse)
            buzResponse = Collections.emptyMap();

        return BaseResponse.builder()
                .payload(new Payload<>(buzResponse))
                .status(
                        Status.builder()
                                .statusCode(httpStatus.value())
                                .statusValue(httpStatus.name()).build())
                .build();
    }

    public static Collection<Errors> getNoContentErrorList() {
        Collection<Errors> errors = new ArrayList<>();
        errors.add(
                Errors.builder()
                        .message(ErrorCode.NO_DATA_FOUND)
                        .errorCode(String.valueOf(Errors.ERROR_TYPE.DATABASE.toCode()))
                        .errorType(Errors.ERROR_TYPE.DATABASE.toValue())
                        .level(Errors.SEVERITY.LOW.name())
                        .build()
        );
        return errors;
    }


    public static String generateUserId(String userName, String boundValue) {
logger.info("Inside generateUserId");
        Random random = new Random();
        int rValue = random.nextInt(Integer.parseInt(boundValue));
        // Logic to generate a unique ID based on the name and some random numeric value
        String prefix = userName.substring(0, Math.min(userName.length(), 4)).toUpperCase();
        String randomNumericPart = String.format("%04d", rValue);

        return prefix + randomNumericPart;
    }

    public static BaseResponse getErrorResponse(HttpStatus httpStatus, Collection<Errors> errors) {

        BaseResponse response = new BaseResponse();

        response.setPayload(null);

        response.setStatus(
                Status.builder()
                        .statusCode(httpStatus.value())
                        .statusValue(httpStatus.name())
                        .build()
        );

        response.setErrors(errors == null ? Collections.emptyList() : errors);

        return response;
    }

    public static BaseResponse error(
            HttpStatus status,
            String message,
            Errors.ERROR_TYPE type) {

        Collection<Errors> errors = new ArrayList<>();
        errors.add(
                Errors.builder()
                        .message(message)
                        .errorCode(String.valueOf(type.toCode()))
                        .errorType(type.toValue())
                        .level(Errors.SEVERITY.HIGH.name())
                        .build()
        );

        return GsUtils.getErrorResponse(status, errors);
    }
}
