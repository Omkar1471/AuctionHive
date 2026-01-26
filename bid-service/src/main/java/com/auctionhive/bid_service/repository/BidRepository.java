package com.auctionhive.bid_service.repository;

import com.auctionhive.bid_service.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findByAuctionIdAndItemIdOrderByCreatedAtDesc(
            Long auctionId,
            Long itemId
    );

    Optional<Bid> findFirstByAuctionIdAndItemIdAndHighestBidTrue(
            Long auctionId,
            Long itemId
    );

    List<Bid> findByBidderIdOrderByCreatedAtDesc(Long bidderId);

    @Modifying
    @Query("""
        UPDATE Bid b
        SET b.highestBid = false
        WHERE b.auctionId = :auctionId
          AND b.itemId = :itemId
          AND b.highestBid = true
    """)
    void demoteHighestBid(Long auctionId, Long itemId);
}
