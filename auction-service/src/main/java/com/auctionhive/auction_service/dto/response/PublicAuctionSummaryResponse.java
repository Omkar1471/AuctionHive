package com.auctionhive.auction_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PublicAuctionSummaryResponse {

    private Long auctionId;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String primaryImageUrl;
}
