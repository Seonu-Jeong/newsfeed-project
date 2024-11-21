package org.sparta.newsfeed.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sparta.newsfeed.entity.enums.FriendType;

@Getter
@Builder
@AllArgsConstructor
public class UserFriendResponseDto {

    private final Long userId;

    private final String userName;

    private final String userImage;

    private final FriendType friendStatus;

    private final String representFriendName;

    private final Integer relationFriendCount;
}
