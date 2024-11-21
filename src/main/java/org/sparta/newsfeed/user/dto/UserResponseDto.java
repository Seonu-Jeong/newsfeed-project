package org.sparta.newsfeed.user.dto;

import lombok.Getter;
import org.sparta.newsfeed.entity.User;

@Getter
public class UserResponseDto {

    private String nickname;

    private String userImage;

    private String selfComment;

    private Long postCount;

    private Long friendCount;

    public UserResponseDto(String nickname, String userImage, String selfComment, Long postCount, Long friendCount) {
        this.friendCount = friendCount;
        this.postCount = postCount;
        this.selfComment = selfComment;
        this.userImage = userImage;
        this.nickname = nickname;
    }

    public UserResponseDto(User user){
        this.nickname = user.getNickname();
        this.userImage = user.getUserImage();
        this.selfComment = user.getSelfComment();
        this.postCount = (long) user.getBoards().size();
        this.friendCount = (long) (user.getRequestedUsers().size() + user.getPostedUsers().size());
    }
}
