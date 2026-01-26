package com.auctionhive.auction_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PublicAuctionDetailsResponse {

    private Long auctionId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<PublicAuctionItemResponse> items;
}
