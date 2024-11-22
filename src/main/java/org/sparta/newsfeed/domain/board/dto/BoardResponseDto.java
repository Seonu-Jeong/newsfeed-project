package org.sparta.newsfeed.domain.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String postImage;
    private String postBody;
    private LocalDateTime createdAt;


    public BoardResponseDto(Long id, String postImage, String postBody, LocalDateTime createdAt) {

        this.id = id;
        this.postImage = postImage;
        this.postBody = postBody;
        this.createdAt = createdAt;
    }
}