package org.sparta.newsfeed.domain.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.domain.comment.dto.CommentRequestDto;
import org.sparta.newsfeed.domain.comment.dto.CommentResponseDto;
import org.sparta.newsfeed.domain.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.sparta.newsfeed.global.constant.Const.LOGIN_USER;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{boardId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long boardId) {
        List<CommentResponseDto> comments = commentService.getComments(boardId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long boardId,
                                                            @Validated @RequestBody CommentRequestDto requestDto,
                                                            @SessionAttribute(name = LOGIN_USER) Long loginId) {
        CommentResponseDto responseDto = commentService.createComment(loginId, boardId, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long boardId,
                                                            @PathVariable Long commentId,
                                                            @Valid @RequestBody CommentRequestDto requestDto,
                                                            @SessionAttribute(name = LOGIN_USER) Long loginId){
        CommentResponseDto responseDto = commentService.updateComment(loginId, boardId, commentId, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long boardId,
                              @PathVariable Long commentId,
                              @SessionAttribute(name = LOGIN_USER) Long loginId) {
        commentService.deleteComment(loginId, boardId, commentId);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
