package com.auctionhive.user_service.utils;

public enum ErrorCodes {
    ER00P1("Response Url is Missing"),

    ER00P2("Null Request"),
    ER00P3("Authorization is Missing "),
    ER00P4("Api key is Missing"),
    ER00P5("MetaData is Missing"),
    ER00P6("Mandatory request parameter Missing"),
    ER00P7("Invalid request parameter");
    private final String stringValue;

    ErrorCodes(final String s) {
        stringValue = s;
    }

    public String toFaceValue() {
        return stringValue;
    }


}
