package com.auctionhive.bid_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "bids",
        indexes = {
                @Index(name = "idx_bid_auction_item", columnList = "auction_id,item_id"),
                @Index(name = "idx_bid_bidder", columnList = "bidder_id")
        }
)
@Getter
@Setter
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auction_id", nullable = false)
    private Long auctionId;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "bidder_id", nullable = false)
    private Long bidderId;

    @Column(name = "bid_amount", nullable = false)
    private Double bidAmount;

    @Column(name = "is_highest", nullable = false)
    private boolean highestBid;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
