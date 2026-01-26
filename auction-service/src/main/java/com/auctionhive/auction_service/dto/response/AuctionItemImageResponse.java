package com.auctionhive.auction_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionItemImageResponse {

    private Long id;
    private String imageUrl;
    private boolean primary;
}
