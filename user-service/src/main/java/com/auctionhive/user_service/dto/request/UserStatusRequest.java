package com.auctionhive.user_service.dto.request;

import com.auctionhive.user_service.entity.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatusRequest {

    private Status status;
}
