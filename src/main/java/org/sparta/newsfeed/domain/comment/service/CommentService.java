package org.sparta.newsfeed.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.domain.board.repository.BoardRepository;
import org.sparta.newsfeed.domain.comment.dto.CommentRequestDto;
import org.sparta.newsfeed.domain.comment.dto.CommentResponseDto;
import org.sparta.newsfeed.domain.comment.repository.CommentRepository;
import org.sparta.newsfeed.global.entity.Board;
import org.sparta.newsfeed.global.entity.Comment;
import org.sparta.newsfeed.global.entity.User;
import org.sparta.newsfeed.domain.user.repository.UserRepository;
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

        return commentRepository.findCommentsByBoardId(boardId).stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }

    public CommentResponseDto createComment(Long loginId, Long boardId, CommentRequestDto requestDto) {

        User loginUser = userRepository.findByIdOrElseThrow(loginId);
        Board board = boardRepository.findBoardById(boardId);

        Comment comment = new Comment(requestDto.getComment(), loginUser, board);
        Comment saveComment = commentRepository.save(comment);

        return CommentResponseDto.toDto(saveComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long loginId, Long boardId, Long commentId, CommentRequestDto requestDto) {
        User loginUser = userRepository.findByIdOrElseThrow(loginId);
        Board board = boardRepository.findBoardById(boardId);
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        checkUrlPath(board, comment);
        checkAuthorizedComment(commentId, loginUser);

        comment.setComment(requestDto.getComment());

        return CommentResponseDto.toDto(comment);
    }

    public void deleteComment(Long loginId, Long boardId, Long commentId) {
        User loginUser = userRepository.findByIdOrElseThrow(loginId);
        Board board = boardRepository.findBoardById(boardId);
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        checkUrlPath(board, comment);
        checkAuthorizedComment(commentId, loginUser);

        commentRepository.delete(comment);
    }

    private void checkAuthorizedComment(Long commentId, User loginUser) {
        loginUser.getComments()
                .stream()
                .filter(c -> c.getId().equals(commentId))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한을 가지고 있지 않습니다."));
    }

    private void checkUrlPath(Board board, Comment comment) {
        if (comment.getBoard() != board) {
            String message = String.format("해당 경로는 맞지 않습니다. url : api/boards/%s/comments/%s ", board.getId(), comment.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }
    }
}
