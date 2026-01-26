package com.auctionhive.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {

    @NotBlank(message = "Full name is required")
    private String name;
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid mobile number format"
    )
    private String phone;

}
