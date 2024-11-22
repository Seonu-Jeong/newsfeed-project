package org.sparta.newsfeed.domain.comment.like.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.domain.comment.like.Service.CommentLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.sparta.newsfeed.global.constant.Const.LOGIN_USER;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{boardId}/comments/{commentId}/like")
    public ResponseEntity<Void> addLike(@PathVariable Long boardId,
                                        @PathVariable Long commentId,
                                        @SessionAttribute(name = LOGIN_USER) Long loginId) {
        commentLikeService.addLike(loginId, boardId, commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/comments/{commentId}/like")
    public ResponseEntity<Void> deleteLike(@PathVariable Long boardId,
                                           @PathVariable Long commentId,
                                           @SessionAttribute(name = LOGIN_USER) Long loginId) {
        commentLikeService.deleteLike(loginId, boardId, commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
