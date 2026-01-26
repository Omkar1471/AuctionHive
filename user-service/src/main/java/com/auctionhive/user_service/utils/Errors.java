package com.auctionhive.user_service.utils;

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

    public enum SEVERITY {
        CRITICAL,
        HIGH,
        MEDIUM,
        LOW
    }

    public enum ERROR_TYPE {

        SYSTEM("SYSTEM", 1000),
        USER("USER", 2000),
        DATABASE("DATABASE", 3000),
        NETWORK("NETWORK", 4000),
        ASSET_COST_ERROR("ASSET_COST_ERROR", 4500),
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
