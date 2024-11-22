package org.sparta.newsfeed.domain.board.like.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.domain.board.like.service.BoardLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.sparta.newsfeed.global.constant.Const.LOGIN_USER;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @PostMapping("/{boardId}/like")
    public ResponseEntity<Void> addBoardLike (@PathVariable Long boardId, @SessionAttribute(name = LOGIN_USER) Long loginId) {

        boardLikeService.addLike(boardId, loginId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{boardId}/like")
    public ResponseEntity<Void> deleteBoardLike(@PathVariable Long boardId,
                                                @SessionAttribute(name = LOGIN_USER) Long loginId) {
        boardLikeService.deleteLike(loginId, boardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
