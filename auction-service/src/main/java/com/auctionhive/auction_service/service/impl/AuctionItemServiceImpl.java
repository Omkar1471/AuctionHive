package com.auctionhive.auction_service.service.impl;

import com.auctionhive.auction_service.dto.request.CreateAuctionItemRequest;
import com.auctionhive.auction_service.dto.request.UpdateAuctionItemRequest;
import com.auctionhive.auction_service.entity.Auction;
import com.auctionhive.auction_service.entity.AuctionItem;
import com.auctionhive.auction_service.entity.AuctionStatus;
import com.auctionhive.auction_service.repository.AuctionItemRepository;
import com.auctionhive.auction_service.repository.AuctionRepository;
import com.auctionhive.auction_service.security.RequestContext;
import com.auctionhive.auction_service.service.AuctionItemService;
import com.auctionhive.auction_service.utils.BaseResponse;
import com.auctionhive.auction_service.utils.ErrorCode;
import com.auctionhive.auction_service.utils.Errors;
import com.auctionhive.auction_service.utils.GsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionItemServiceImpl implements AuctionItemService {

    private final AuctionRepository auctionRepository;
    private final AuctionItemRepository auctionItemRepository;

    /* ================= ADD ITEM ================= */

    @Override
    public BaseResponse addItem(Long auctionId,
                                CreateAuctionItemRequest request) {

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

        AuctionItem item = new AuctionItem();
        item.setAuction(auction);
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setBasePrice(request.getBasePrice());
        item.setQuantity(request.getQuantity());

        auctionItemRepository.save(item);

        return GsUtils.getBaseResponse(
                HttpStatus.CREATED,
                "Auction item added successfully"
        );
    }

    /* ================= UPDATE ITEM ================= */

    @Override
    public BaseResponse updateItem(Long auctionId,
                                   Long itemId,
                                   UpdateAuctionItemRequest request) {

        if (auctionId == null || itemId == null || request == null) {
            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        Long sellerId = RequestContext.get().getUserId();

        Auction auction = auctionRepository
                .findByIdAndSellerId(auctionId, sellerId)
                .orElse(null);

        if (auction == null) {
            return GsUtils.error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        if (auction.getStatus() != AuctionStatus.DRAFT) {
            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID_AUCTION_STATE,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        AuctionItem item =
                auctionItemRepository
                        .findByIdAndAuctionId(itemId, auctionId)
                        .orElse(null);

        if (item == null) {
            return GsUtils.error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setBasePrice(request.getBasePrice());
        item.setQuantity(request.getQuantity());

        auctionItemRepository.save(item);

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Auction item updated successfully"
        );
    }

    /* ================= DELETE ITEM ================= */

    @Override
    public BaseResponse deleteItem(Long auctionId, Long itemId) {

        Long sellerId = RequestContext.get().getUserId();

        Auction auction =
                auctionRepository.findByIdAndSellerId(auctionId, sellerId)
                        .orElse(null);

        if (auction == null || auction.getStatus() != AuctionStatus.DRAFT) {
            return GsUtils.error(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID_AUCTION_STATE,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        AuctionItem item =
                auctionItemRepository
                        .findByIdAndAuctionId(itemId, auctionId)
                        .orElse(null);

        if (item == null) {
            return GsUtils.error(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NO_DATA_FOUND,
                    Errors.ERROR_TYPE.AUCTION
            );
        }

        auctionItemRepository.delete(item);

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                "Auction item deleted successfully"
        );
    }

    /* ================= GET ITEMS ================= */

    @Override
    public BaseResponse getItemsByAuction(Long auctionId) {

        return GsUtils.getBaseResponse(
                HttpStatus.OK,
                auctionItemRepository.findByAuctionId(auctionId)
        );
    }
}
