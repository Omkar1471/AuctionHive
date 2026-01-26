package com.auctionhive.bid_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "highest_bids",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_highest_bid_auction_item",
                        columnNames = {"auction_id", "item_id"}
                )
        }
)
@Getter
@Setter
public class HighestBid {

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

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
