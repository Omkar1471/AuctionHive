package com.auctionhive.auction_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PublicAuctionItemResponse {

    private Long itemId;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private Integer quantity;
    private List<PublicAuctionImageResponse> images;
}
