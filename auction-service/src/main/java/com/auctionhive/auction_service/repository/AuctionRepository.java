package com.auctionhive.auction_service.repository;

import com.auctionhive.auction_service.entity.Auction;
import com.auctionhive.auction_service.entity.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    /* ================= SELLER ================= */

    Optional<Auction> findByIdAndSellerId(Long id, Long sellerId);

    List<Auction> findAllBySellerId(Long sellerId);


    /* ================= PUBLIC ================= */

    @Query("""
        SELECT a FROM Auction a
        WHERE a.status = :status
          AND a.startTime <= :now
          AND a.endTime >= :now
    """)
    List<Auction> findAllLiveAuctions(
            @Param("now") LocalDateTime now,
            @Param("status") AuctionStatus status
    );

    /* DEFAULT METHOD FOR SERVICE SIMPLICITY */
    default List<Auction> findAllLiveAuctions(LocalDateTime now) {
        return findAllLiveAuctions(now, AuctionStatus.LIVE);
    }
}
