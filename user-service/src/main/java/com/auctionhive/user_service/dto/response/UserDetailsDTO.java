package com.auctionhive.user_service.dto.response;

import com.auctionhive.user_service.entity.Role;
import com.auctionhive.user_service.entity.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserDetailsDTO {
    private Long id;

    private String name;


    private String email;


    private Role role;


    private Status status;

    private String phone;
}
