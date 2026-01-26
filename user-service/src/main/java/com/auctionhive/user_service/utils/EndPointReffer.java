package com.auctionhive.user_service.utils;

public class EndPointReffer {

    public static final String SEND_OTP = "/send-otp";
    public static final String VERIFY_OTP = "/verify-otp";
    public static final String USER_REGISTER ="/register";
    public static final String USER_LOGIN = "/login";
    public static final String USER_GETUSERBYID ="/{userid}" ;

    private EndPointReffer(){

    }
}
