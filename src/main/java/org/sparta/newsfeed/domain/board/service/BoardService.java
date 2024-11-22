package org.sparta.newsfeed.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.domain.board.dto.BoardRequestDto;
import org.sparta.newsfeed.domain.board.dto.BoardResponseDto;
import org.sparta.newsfeed.domain.board.dto.BoardResponsePage;
import org.sparta.newsfeed.domain.board.repository.BoardRepository;
import org.sparta.newsfeed.global.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        Board savedboard = boardRepository.save(Board.from(requestDto));
        return savedboard.to();
    }

    //전체 목록 조회
    public List<BoardResponseDto> getBoardList() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(Board::to)
                .collect(Collectors.toList());
    }

    //페이징 적용 조회
    public BoardResponsePage getBoardListWithPaging(int page, int size, String criteria) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, criteria));
        Page<Board> todos = boardRepository.findAll(pageable);
        return new BoardResponsePage(todos);
    }

    public BoardResponseDto getTodo(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + boardId));
        return board.to();
    }

    @Transactional //영속성 안에서 엔티티가 바뀌면 자동저장
    public void updateTodo(Long boardId, BoardRequestDto requestDto) {
        Board board = boardRepository.findBoardById(boardId);
        board.updateData(requestDto);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.findBoardById(boardId);
        boardRepository.deleteById(boardId);
    }
}
