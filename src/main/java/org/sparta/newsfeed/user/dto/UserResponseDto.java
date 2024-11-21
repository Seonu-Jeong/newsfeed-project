package org.sparta.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private String nickname;

    private String userImage;

    private String comment;

    private Long postCount;

    private Long friendCount;

    public UserResponseDto(Long friendCount, Long postCount, String comment, String userImage, String nickname) {
        this.friendCount = friendCount;
        this.postCount = postCount;
        this.comment = comment;
        this.userImage = userImage;
        this.nickname = nickname;
    }
}
