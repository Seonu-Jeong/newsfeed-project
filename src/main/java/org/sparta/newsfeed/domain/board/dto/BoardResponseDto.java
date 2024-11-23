package org.sparta.newsfeed.domain.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.newsfeed.global.entity.BoardLike;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String postImage;
    private String postBody;
    private Long boardLikeCount;
    private LocalDateTime createdAt;

    public BoardResponseDto(Long id, String postImage, String postBody, Long boardLikeCount, LocalDateTime createdAt) {

        this.id = id;
        this.postImage = postImage;
        this.postBody = postBody;
        this.boardLikeCount = boardLikeCount;
        this.createdAt = createdAt;
    }
}