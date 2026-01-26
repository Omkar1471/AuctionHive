package com.auctionhive.user_service.utils;


public class ErrorCode {
    public static final String NO_DATA_FOUND = "No data found against provided request.";
    public static final String NULL_RESPONSE_FROM_SERVICE = "Null response form third party sms service ";

    public static final String SEND_SMS_FAILED= "OTP send failed";

    public static final String WRONG_OTP = "We're sorry, but the OTP you entered is incorrect. Please double-check the code and try again. Make sure you're using the latest OTP sent to your registered mobile number....!";
    public static final String NO_CATEGORY_FOUND = "No category found against provided request.";

    public static final String API_CONFIG_IS_NULL = "ApiConfig is null.";
    public static final String NO_ID_FOUND = "Product ID is required";
    public static final String NO_USER_NAME_FOUND ="User name is required";

    public static final String SEND_OTP_FAILED ="Send otp failed please try again after some time .. !";
    public static final String PASSWORD_NOT_MATCH = "Password and confirmation password does not match";
    public static final String USER_PASSWORD_NOT_MATCH ="UserName password does not match";

    public static final String USERNAME_MOBILE_REQUIRED ="Username/ Mobile Number/ Email required to login";


    public static final String USER_NOT_FOUND_PLEASE_REGISTER ="User not found please register before login";
    public static final String EMAIL_ALREADY_EXISTS ="Email already exists" ;
    public static final String INTERNAL_SERVER_ERROR = "Internal server error occurred. Please try again later.";
    public static final String UNAUTHORIZED = "The Person is Unauthorized";
    public static final String INVALID_PASSWORD ="Invalid Password" ;
    public static final String PASSWORD_MISMATCH ="Password Mismatch" ;
    public static final String INVALID_TOKEN = "Token Invalid";
    public static final String INVALID_ROLE = "Invalid Role";
    public static final String INVALID_CREDENTIALS = "Invalid Credentials";
    public static final String ACCESS_DENIED = "Access Denied";
    public static final String VALIDATION_ERROR ="Validation Error" ;
    public static final String BAD_REQUEST ="Bad Request" ;
    public static final String BUSINESS_ERROR ="Business related error" ;


    private ErrorCode() {

    }
}