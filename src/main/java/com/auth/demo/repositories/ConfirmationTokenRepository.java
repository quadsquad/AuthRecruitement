package com.auth.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.auth.demo.entities.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
