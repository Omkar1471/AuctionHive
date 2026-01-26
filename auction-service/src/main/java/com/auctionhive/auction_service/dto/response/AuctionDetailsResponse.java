package com.auctionhive.auction_service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionDetailsResponse {

    private Long auctionId;
    private String title;
    private String description;
    private BigDecimal basePrice;
    private String category;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String status;
    private String visibility;

    private List<String> images;
}
