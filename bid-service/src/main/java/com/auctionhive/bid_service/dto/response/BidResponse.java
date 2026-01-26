package com.auctionhive.bid_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BidResponse {

    private Long bidId;

    private Long auctionId;

    private Long itemId;

    private Long bidderId;

    private Double amount;

    private boolean highestBid;

    private LocalDateTime createdAt;
}
