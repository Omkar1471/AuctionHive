package com.auctionhive.auction_service.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Errors {

    @JsonProperty("sMessage")
    private String message;

    @JsonProperty("sErrorCode")
    private String errorCode;

    @JsonProperty("sErrorType")
    private String errorType;

    @JsonProperty("sLevel")
    private String level;

    /* ===================== SEVERITY ===================== */

    public enum SEVERITY {
        CRITICAL,
        HIGH,
        MEDIUM,
        LOW
    }

    /* ===================== ERROR TYPE ===================== */

    public enum ERROR_TYPE {

        SYSTEM("SYSTEM", 1000),
        USER("USER", 2000),
        DATABASE("DATABASE", 3000),
        NETWORK("NETWORK", 4000),

        /* ===== AUCTION / BID DOMAIN ===== */

        AUCTION("AUCTION", 6000),
        BID("BID", 7000),

        /* ===== SECURITY ===== */

        SECURITY("SECURITY", 5000);

        private final int code;
        private final String value;

        ERROR_TYPE(final String value, final int code) {
            this.code = code;
            this.value = value;
        }

        public String toValue() {
            return value;
        }

        public int toCode() {
            return code;
        }
    }
}
