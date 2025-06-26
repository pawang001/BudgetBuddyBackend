package com.budget_buddy.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;

    public UserResponse(String username, String email) {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public UserResponse(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
