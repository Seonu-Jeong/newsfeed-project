package org.sparta.newsfeed.friend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class FriendResponseDto {

    private Long userId;

    private String userName;

    private String userImage;

    private String representFriendName;

    private Integer relationFriendCount;

}
