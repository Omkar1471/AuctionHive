package com.auctionhive.bid_service.repository;

import com.auctionhive.bid_service.entity.HighestBid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HighestBidRepository
        extends JpaRepository<HighestBid, Long> {

    Optional<HighestBid> findByAuctionIdAndItemId(
            Long auctionId,
            Long itemId
    );
}
