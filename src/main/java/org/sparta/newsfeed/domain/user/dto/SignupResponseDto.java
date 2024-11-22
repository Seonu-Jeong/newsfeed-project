package org.sparta.newsfeed.domain.user.dto;

import lombok.Getter;

@Getter
public class SignupResponseDto {

    private String email;

    private String nickname;

    public SignupResponseDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
