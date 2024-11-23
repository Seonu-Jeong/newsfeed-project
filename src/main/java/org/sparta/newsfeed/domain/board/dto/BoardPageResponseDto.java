package org.sparta.newsfeed.domain.board.dto;

import lombok.Getter;
import org.sparta.newsfeed.global.entity.Board;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardPageResponseDto {
    private List<BoardResponseDto> boards;
    private int totalPages;
    private long totalElements;

    public BoardPageResponseDto(Page<Board> page) {
        this.boards = page.getContent().stream()
                .map(Board::toDto)
                .collect(Collectors.toList());
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }

    public BoardPageResponseDto(List<Board> board) {
        boards = board.stream()
                .map(Board::toDto)
                .toList();

    }
}