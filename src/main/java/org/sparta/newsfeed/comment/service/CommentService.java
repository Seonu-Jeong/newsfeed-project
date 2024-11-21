package org.sparta.newsfeed.comment.service;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.board.repository.BoardRepository;
import org.sparta.newsfeed.comment.repository.CommentRepository;
import org.sparta.newsfeed.comment.dto.CommentResponseDto;
import org.sparta.newsfeed.comment.dto.CreateCommentRequestDto;
import org.sparta.newsfeed.entity.Board;
import org.sparta.newsfeed.entity.Comment;
import org.sparta.newsfeed.entity.User;
import org.sparta.newsfeed.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public List<CommentResponseDto> getComments(Long boardId) {
        List<Comment> commentsByBoardId = commentRepository.findCommentsByBoardId(boardId);

        return commentsByBoardId.stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }

    @Transactional
    public CommentResponseDto createComment(Long loginId, Long boardId, CreateCommentRequestDto requestDto) {

        User loginUser = userRepository.findById(loginId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디는 회원이 아닙니다. loginID : " + loginId));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시판은 존재하지 않습니다. boardId : " + boardId));

        Comment comment = new Comment(requestDto.getComment(), loginUser, board);

        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }
}
