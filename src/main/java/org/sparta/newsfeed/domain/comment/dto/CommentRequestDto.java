package org.sparta.newsfeed.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    @NotBlank(message = "빈칸아니어라")
    String comment;
}
