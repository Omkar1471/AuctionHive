package com.auctionhive.bid_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceBidRequest {

    @NotNull(message = "Auction ID is required")
    private Long auctionId;

    @NotNull(message = "Item ID is required")
    private Long itemId;

    @NotNull(message = "Bid amount is required")
    @Min(value = 1, message = "Bid amount must be greater than 0")
    private Double amount;
}
