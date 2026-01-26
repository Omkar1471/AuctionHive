package com.auctionhive.bid_service.service.impl;

import com.auctionhive.bid_service.dto.request.PlaceBidRequest;
import com.auctionhive.bid_service.entity.Bid;
import com.auctionhive.bid_service.entity.HighestBid;
import com.auctionhive.bid_service.repository.BidRepository;
import com.auctionhive.bid_service.repository.HighestBidRepository;
import com.auctionhive.bid_service.security.RequestContext;
import com.auctionhive.bid_service.service.BidService;
import com.auctionhive.bid_service.utils.BaseResponse;
import com.auctionhive.bid_service.utils.ErrorCode;
import com.auctionhive.bid_service.utils.Errors;
import com.auctionhive.bid_service.utils.GsUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final HighestBidRepository highestBidRepository;

    @Override
    @Transactional
    public BaseResponse placeBid(PlaceBidRequest request) {

        if (request == null) {
            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.BID
            );
        }

        Long buyerId = RequestContext.get().getUserId();
        if (buyerId == null) {
            return GsUtils.error(
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.UNAUTHORIZED,
                    Errors.ERROR_TYPE.SECURITY
            );
        }

        highestBidRepository
                .findByAuctionIdAndItemId(
                        request.getAuctionId(),
                        request.getItemId()
                )
                .ifPresent(hb -> {
                    if (request.getAmount() <= hb.getBidAmount()) {
                        throw new IllegalArgumentException("Bid must be higher than current highest");
                    }
                });

        bidRepository.demoteHighestBid(
                request.getAuctionId(),
                request.getItemId()
        );

        Bid bid = new Bid();
        bid.setAuctionId(request.getAuctionId());
        bid.setItemId(request.getItemId());
        bid.setBidderId(buyerId);
        bid.setBidAmount(request.getAmount());
        bid.setHighestBid(true);

        bidRepository.save(bid);

        HighestBid highestBid =
                highestBidRepository
                        .findByAuctionIdAndItemId(
                                request.getAuctionId(),
                                request.getItemId()
                        )
                        .orElse(new HighestBid());

        highestBid.setAuctionId(request.getAuctionId());
        highestBid.setItemId(request.getItemId());
        highestBid.setBidderId(buyerId);
        highestBid.setBidAmount(request.getAmount());

        highestBidRepository.save(highestBid);

        return GsUtils.getBaseResponse(
                HttpStatus.CREATED,
                "Bid placed successfully"
        );
    }

    @Override
    public BaseResponse getMyBids() {

        Long buyerId = RequestContext.get().getUserId();

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                bidRepository.findByBidderIdOrderByCreatedAtDesc(buyerId)
        );
    }

    @Override
    public BaseResponse getBidHistory(Long auctionId, Long itemId) {

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                bidRepository.findByAuctionIdAndItemIdOrderByCreatedAtDesc(
                        auctionId,
                        itemId
                )
        );
    }
}
