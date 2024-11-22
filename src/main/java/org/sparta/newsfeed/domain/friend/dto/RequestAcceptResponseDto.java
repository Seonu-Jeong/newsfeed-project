package org.sparta.newsfeed.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sparta.newsfeed.global.entity.enums.FriendType;

@Getter
@Builder
@AllArgsConstructor
public class RequestAcceptResponseDto {

    private final String userName;

    private final FriendType friendStatus;
}
