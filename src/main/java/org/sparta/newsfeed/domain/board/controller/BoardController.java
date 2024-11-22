package org.sparta.newsfeed.domain.board.controller;

import lombok.RequiredArgsConstructor;
//import org.sparta.newsfeed.board.service.BoardService;
import org.sparta.newsfeed.domain.board.dto.BoardRequestDto;
import org.sparta.newsfeed.domain.board.dto.BoardResponseDto;
import org.sparta.newsfeed.domain.board.dto.BoardResponsePage;
import org.sparta.newsfeed.domain.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    //일정 생성하기
    @PostMapping()
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto requestDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(boardService.createBoard(requestDto));
    }

    //전체 일정 조회
    @GetMapping()
    public ResponseEntity<BoardResponsePage> getBoardList(@RequestParam(required = false, defaultValue = "0") int page,
                                                          @RequestParam(required = false, defaultValue = "10") int size,
                                                          @RequestParam(required = false, defaultValue = "modifiedAt") String criteria){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.getBoardListWithPaging(page, size, criteria));
    }

    //선택 일정 조회
    @GetMapping("/{board_Id}")
    public ResponseEntity<BoardResponseDto>getTodo(@PathVariable Long board_Id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.getTodo(board_Id));
    }

    //선택 일정 수정
    @PutMapping("/{board_Id}")
    public ResponseEntity<Void> updateBoard(
            @PathVariable Long board_Id,
            @RequestBody BoardRequestDto requestDto
    ){
        boardService.updateTodo(board_Id, requestDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }

    //선택 일정 삭제
    @DeleteMapping("/{board_id}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable Long board_id
    ){
        boardService.deleteBoard(board_id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }
}


