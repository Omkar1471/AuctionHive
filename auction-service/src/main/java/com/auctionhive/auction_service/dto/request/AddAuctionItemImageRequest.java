package com.auctionhive.auction_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAuctionItemImageRequest {

    @NotBlank
    private String imageUrl;

    @NotNull
    private Boolean primaryImage;

    // optional ordering (0,1,2...)
    private Integer displayOrder;
}
