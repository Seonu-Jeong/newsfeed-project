package org.sparta.newsfeed.comment.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.comment.dto.CommentResponseDto;
import org.sparta.newsfeed.comment.dto.CreateCommentRequestDto;
import org.sparta.newsfeed.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long boardId, @Valid @RequestBody CreateCommentRequestDto requestDto, HttpServletRequest request) {

        HttpSession session = request.getSession();
       // Long loginId = (Long) session.getAttribute(LOGIN_USER);
        Long loginId = 1L;

        CommentResponseDto responseDto = commentService.createComment(loginId, boardId, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
