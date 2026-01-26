package com.auctionhive.auction_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAuctionItemRequest {

    @NotBlank(message = "Item name is required")
    private String name;

    @NotBlank(message = "Item description is required")
    private String description;

    @NotNull(message = "Base price is required")
    @Min(value = 1, message = "Base price must be greater than 0")
    private Double basePrice;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
