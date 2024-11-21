package org.sparta.newsfeed.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponseDto {
    private String errorMessage;
}
