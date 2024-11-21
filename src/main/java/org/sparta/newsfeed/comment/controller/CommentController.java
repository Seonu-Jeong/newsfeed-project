package org.sparta.newsfeed.comment.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.comment.dto.CommentResponseDto;
import org.sparta.newsfeed.comment.dto.CommentRequestDto;
import org.sparta.newsfeed.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.sparta.newsfeed.constant.Const.LOGIN_USER;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{boardId}/comment")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long boardId) {
        List<CommentResponseDto> comments = commentService.getComments(boardId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long boardId, @Valid @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        Long loginId = 1L; //  getLoginId(request);
        CommentResponseDto responseDto = commentService.createComment(loginId, boardId, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long boardId, @PathVariable Long commentId, CommentRequestDto requestDto, HttpServletRequest request) {
        Long loginId = 1L; //  getLoginId(request);
        CommentResponseDto responseDto = commentService.updateComment(loginId, boardId, commentId, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }



    private static void getLoginId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long loginId = (Long) session.getAttribute(LOGIN_USER);
    }
}
