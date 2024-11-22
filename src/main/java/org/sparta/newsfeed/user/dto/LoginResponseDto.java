package org.sparta.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final Long userId;

    public LoginResponseDto(Long userId) {
        this.userId = userId;
    }

}
