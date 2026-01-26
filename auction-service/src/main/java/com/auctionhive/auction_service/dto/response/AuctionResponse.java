package com.auctionhive.auction_service.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionResponse {

    private Long auctionId;
    private String message;
}
