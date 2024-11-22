package org.sparta.newsfeed.comment.dto;

import lombok.Getter;
import org.sparta.newsfeed.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;

    private String nikename;

    private String comment;

    private Long commentLikeCount;

    private LocalDateTime createAt;

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getUser().getNickname(), comment.getComment(), comment.getCreatedAt(), (long) comment.getCommentLike().size());
    }

    public CommentResponseDto(Long id, String nikename, String comment, LocalDateTime createAt, Long commentLikeCount) {
        this.id = id;
        this.nikename = nikename;
        this.comment = comment;
        this.createAt = createAt;
        this.commentLikeCount = commentLikeCount;
    }
}
