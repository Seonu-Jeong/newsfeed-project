package org.sparta.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private String nickname;

    private String userImage;

    private String comment;

    private Long postCount;

    private Long friendCount;

    public UserResponseDto(String nickname, String userImage, String comment, Long postCount, Long friendCount) {
        this.friendCount = friendCount;
        this.postCount = postCount;
        this.comment = comment;
        this.userImage = userImage;
        this.nickname = nickname;
    }
}
