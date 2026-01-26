package com.auctionhive.auction_service.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Collection;
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString
public class BaseResponse {

    @JsonProperty("oBody")
    private Payload<?> payload;

    @JsonProperty("oStatus")
    private Status status;

    @JsonProperty("aError")
    private Collection<Errors> errors;
    @JsonProperty("sJwt")
    private String jwt;
    @JsonProperty("sMessage")
    private String message;
    public BaseResponse() { }

    public BaseResponse(String s, String successfulLogin) {
        this.jwt = s;
        this.message = successfulLogin;
    }


    public static Builder builder() {
        return new Builder();
    }



    public static class Builder {
        private final BaseResponse baseResponse = new BaseResponse();

        public Builder payload(Payload<?> payload) {
            this.baseResponse.setPayload(payload);
            return this;
        }

        public Builder errors(Collection<Errors> errors) {
            this.baseResponse.setErrors(errors);
            return this;
        }

        public Builder status(Status status) {
            this.baseResponse.setStatus(status);
            return this;
        }

        public BaseResponse build() {
            return this.baseResponse;
        }
    }
}
