package com.auctionhive.auction_service.dto.request;

import com.auctionhive.auction_service.entity.AuctionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionStatusUpdateRequest {

    @NotNull
    private AuctionStatus status;
}
