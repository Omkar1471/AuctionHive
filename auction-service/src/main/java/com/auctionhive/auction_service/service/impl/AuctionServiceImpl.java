package com.auctionhive.auction_service.service.impl;

import com.auctionhive.auction_service.dto.request.*;
import com.auctionhive.auction_service.entity.Auction;
import com.auctionhive.auction_service.entity.AuctionStatus;
import com.auctionhive.auction_service.entity.AuctionVisibility;
import com.auctionhive.auction_service.repository.AuctionRepository;
import com.auctionhive.auction_service.security.RequestContext;
import com.auctionhive.auction_service.service.AuctionService;
import com.auctionhive.auction_service.utils.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;

    /* ================= SELLER ================= */

    @Override
    public BaseResponse createAuction(CreateAuctionRequest request) {

        if (request == null) {
            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        Long sellerId = RequestContext.get().getUserId();
        if (sellerId == null) {
            return GsUtils.error(
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.UNAUTHORIZED,
                    Errors.ERROR_TYPE.SECURITY
            );
        }

        if (request.getTitle() == null || request.getTitle().isBlank()
                || request.getStartTime() == null
                || request.getEndTime() == null) {

            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID_REQUEST,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        if (request.getStartTime().isBefore(LocalDateTime.now())
                || request.getEndTime().isBefore(request.getStartTime())) {

            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID_TIME_RANGE,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        Auction auction = new Auction();
        auction.setSellerId(sellerId);
        auction.setTitle(request.getTitle());
        auction.setDescription(request.getDescription());
        auction.setStartTime(request.getStartTime());
        auction.setEndTime(request.getEndTime());

        auction.setStatus(AuctionStatus.DRAFT);
        auction.setVisibility(AuctionVisibility.PRIVATE);

        // createdAt & updatedAt handled by Hibernate
        auctionRepository.save(auction);

        return GsUtils.getBaseResponse(
                HttpStatus.CREATED,
                "Auction created successfully"
        );
    }

    @Override
    public BaseResponse updateAuction(Long auctionId,
                                      UpdateAuctionRequest request) {

        if (auctionId == null || request == null) {
            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        Long sellerId = RequestContext.get().getUserId();
        if (sellerId == null) {
            return GsUtils.error(
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.UNAUTHORIZED,
                    Errors.ERROR_TYPE.SECURITY
            );
        }

        Optional<Auction> optionalAuction =
                auctionRepository.findByIdAndSellerId(auctionId, sellerId);

        if (optionalAuction.isEmpty()) {
            return GsUtils.error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        Auction auction = optionalAuction.get();

        if (auction.getStatus() != AuctionStatus.DRAFT) {
            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID_AUCTION_STATE,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            auction.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            auction.setDescription(request.getDescription());
        }

        if (request.getEndTime() != null) {
            if (request.getEndTime().isBefore(LocalDateTime.now())
                    || request.getEndTime().isBefore(auction.getStartTime())) {

                return GsUtils.error(
                        HttpStatus.BAD_REQUEST,
                        ErrorCode.INVALID_TIME_RANGE,
                        Errors.ERROR_TYPE.AUCTION
                );
            }
            auction.setEndTime(request.getEndTime());
        }

        auctionRepository.save(auction);

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Auction updated successfully"
        );
    }

    @Override
    public BaseResponse getMyAuctions() {

        Long sellerId = RequestContext.get().getUserId();
        if (sellerId == null) {
            return GsUtils.error(
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.UNAUTHORIZED,
                    Errors.ERROR_TYPE.SECURITY
            );
        }

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                auctionRepository.findAllBySellerId(sellerId)
        );
    }

    @Override
    public BaseResponse getMyAuctionDetails(Long auctionId) {

        Long sellerId = RequestContext.get().getUserId();

        Optional<Auction> optionalAuction =
                auctionRepository.findByIdAndSellerId(auctionId, sellerId);

        if (optionalAuction.isEmpty()) {
            return GsUtils.error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                optionalAuction.get()
        );
    }

    /* ================= ADMIN ================= */

    @Override
    public BaseResponse updateAuctionStatus(Long auctionId,
                                            AuctionStatusUpdateRequest request) {

        if (auctionId == null || request == null || request.getStatus() == null) {
            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        Optional<Auction> optionalAuction =
                auctionRepository.findById(auctionId);

        if (optionalAuction.isEmpty()) {
            return GsUtils.error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        Auction auction = optionalAuction.get();

        auction.setStatus(request.getStatus());
        auctionRepository.save(auction);

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Auction status updated successfully"
        );
    }

    @Override
    public BaseResponse getAllAuctionsForAdmin() {

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                auctionRepository.findAll()
        );
    }

    /* ================= PUBLIC ================= */

    @Override
    public BaseResponse getPublicAuctions() {

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                auctionRepository.findAllLiveAuctions(LocalDateTime.now())
        );
    }

    @Override
    public BaseResponse getAuctionDetails(Long auctionId) {

        Optional<Auction> optionalAuction =
                auctionRepository.findById(auctionId);

        if (optionalAuction.isEmpty()) {
            return GsUtils.error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                optionalAuction.get()
        );
    }
}
