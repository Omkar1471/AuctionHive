package com.auctionhive.user_service.dto.request;

import com.auctionhive.user_service.entity.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterDTO {

    @NotBlank(message = "Full name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one number"
    )
    private String password;

    @NotNull(message = "Role is required. Allowed values: BUYER, SELLER")
    private Role role;

    // OPTIONAL mobile number
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid mobile number format"
    )
    private String phone;
}
