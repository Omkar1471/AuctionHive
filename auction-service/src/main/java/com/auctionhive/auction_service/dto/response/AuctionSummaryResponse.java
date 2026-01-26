package com.auctionhive.auction_service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionSummaryResponse {

    private Long auctionId;
    private String title;
    private BigDecimal basePrice;
    private LocalDateTime endTime;
    private String primaryImage;
}
