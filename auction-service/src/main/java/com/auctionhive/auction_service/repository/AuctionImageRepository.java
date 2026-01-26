package com.auctionhive.auction_service.repository;

import com.auctionhive.auction_service.entity.AuctionImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionImageRepository
        extends JpaRepository<AuctionImage, Long> {

    List<AuctionImage> findByAuctionId(Long auctionId);

    long countByAuctionId(Long auctionId);
}
