package com.auctionhive.auction_service.repository;

import com.auctionhive.auction_service.entity.AuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuctionItemRepository extends JpaRepository<AuctionItem, Long> {

    List<AuctionItem> findByAuctionId(Long auctionId);

    Optional<AuctionItem> findByIdAndAuctionId(Long itemId, Long auctionId);
}
