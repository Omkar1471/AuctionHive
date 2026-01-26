package com.auctionhive.user_service.repository;

import com.auctionhive.user_service.entity.Role;
import com.auctionhive.user_service.entity.Status;
import com.auctionhive.user_service.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    long countByStatus(Status status);
    long countByRole(Role role);

}
