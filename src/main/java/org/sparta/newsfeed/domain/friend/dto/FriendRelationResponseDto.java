package org.sparta.newsfeed.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.sparta.newsfeed.global.entity.enums.FriendType;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class FriendRelationResponseDto {

    private Long userId;

    private String userName;

    private String userImage;

    private FriendType friendStatus;

    private String representFriendName;

    private Integer relationFriendCount;

    public FriendRelationResponseDto(FriendResponseDto friendResponseDto) {
        this.userId = friendResponseDto.getUserId();
        this.userName = friendResponseDto.getUserName();
        this.userImage = friendResponseDto.getUserImage();
        this.relationFriendCount = friendResponseDto.getRelationFriendCount();
        this.representFriendName = friendResponseDto.getRepresentFriendName();
    }
}
