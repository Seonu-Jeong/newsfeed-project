package org.sparta.newsfeed.domain.board.controller;

import lombok.RequiredArgsConstructor;
//import org.sparta.newsfeed.board.service.BoardService;
import org.sparta.newsfeed.domain.board.dto.BoardRequestDto;
import org.sparta.newsfeed.domain.board.dto.BoardResponseDto;
import org.sparta.newsfeed.domain.board.dto.BoardPageResponseDto;
import org.sparta.newsfeed.domain.board.service.BoardService;
import org.sparta.newsfeed.global.constant.Const;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    //게시글 생성하기
    @PostMapping()
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto requestDto, @SessionAttribute(name = Const.LOGIN_USER) Long userId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(boardService.createBoard(requestDto, userId));
    }

    //전체 게시글 조회
    @GetMapping()
    public ResponseEntity<BoardPageResponseDto> getBoardList(@RequestParam(required = false, defaultValue = "0") int page,
                                                             @RequestParam(required = false, defaultValue = "10") int size,
                                                             @RequestParam(required = false, defaultValue = "modifiedAt") String criteria){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.getBoardListWithPaging(page, size, criteria));
    }

    //친구 게시글 조회
    @GetMapping("/friends")
    public ResponseEntity<List<BoardResponseDto>> getBoardList(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "modifiedAt") String criteria,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.getFriendsBoardList(page, size, criteria, userId));
    }


    //선택 게시글 조회
    @GetMapping("/{board_Id}")
    public ResponseEntity<BoardResponseDto>getBoard(@PathVariable Long board_Id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.getBoard(board_Id));
    }

    //선택 게시글 수정
    @PatchMapping("/{board_Id}")
    public ResponseEntity<Void> updateBoard(
            @PathVariable Long board_Id,
            @RequestBody BoardRequestDto requestDto
    ){
        boardService.updateBoard(board_Id, requestDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }

    //선택 게시글 삭제
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


