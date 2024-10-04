package org.example.barookwallet.infrastructure.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final Long userId;

    public UserNotFoundException(Long userId) {
        super("User not found: " + userId);
        this.userId = userId;
    }
}
