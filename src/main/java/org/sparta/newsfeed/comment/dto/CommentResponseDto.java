package org.sparta.newsfeed.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.sparta.newsfeed.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    @NotBlank
    private Long id;

    @NotBlank
    @Length(max = 30)
    private String nikename;

    @NotBlank
    @Length(max = 255)
    private String comment;

    private LocalDateTime createAt;

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getUser().getNickname(), comment.getComment(), comment.getCreatedAt());
    }

    public CommentResponseDto(Long id, String nikename, String comment, LocalDateTime createAt) {
        this.id = id;
        this.nikename = nikename;
        this.comment = comment;
        this.createAt = createAt;
    }
}
