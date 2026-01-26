package com.auctionhive.bid_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BidHistoryResponse {

    private Long auctionId;

    private List<BidResponse> bids;
}
