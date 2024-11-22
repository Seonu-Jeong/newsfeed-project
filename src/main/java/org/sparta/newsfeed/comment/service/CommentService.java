package org.sparta.newsfeed.comment.service;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.board.repository.BoardRepository;
import org.sparta.newsfeed.comment.dto.CommentRequestDto;
import org.sparta.newsfeed.comment.dto.CommentResponseDto;
import org.sparta.newsfeed.comment.repository.CommentRepository;
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

        return commentRepository.findCommentsByBoardId(boardId).stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }

    public CommentResponseDto createComment(Long loginId, Long boardId, CommentRequestDto requestDto) {

        User loginUser = userRepository.findById(loginId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디는 회원이 아닙니다. loginID : " + loginId));
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시판은 존재하지 않습니다. boardID : " + boardId));

        Comment comment = new Comment(requestDto.getComment(), loginUser, board);
        Comment saveComment = commentRepository.save(comment);

        return CommentResponseDto.toDto(saveComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long loginId, Long boardId, Long commentId, CommentRequestDto requestDto) {
        User loginUser = userRepository.findById(loginId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디는 회원이 아닙니다. loginID : " + loginId));
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시판은 존재하지 않습니다. boardId : " + boardId));

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        if (checkAuthrizedComment(commentId, loginUser, comment)) {

        }
        comment.setComment(requestDto.getComment());

        return CommentResponseDto.toDto(comment);
    }

    private static boolean checkAuthrizedComment(Long commentId, User loginUser, Comment comment) {
        Comment findComment
                = loginUser.getComments()
                .stream()
                .filter(c -> c.getId().equals(commentId))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "테스트"));

        return comment.equals(findComment);
    }

    public void deleteComment(Long loginId, Long boardId, Long commentId) {
        Comment comment = AuthorizedComment(loginId, boardId, commentId);

        commentRepository.delete(comment);
    }



    private Comment AuthorizedComment(Long loginId, Long boardId, Long commentId) {
        User loginUser = userRepository.findById(loginId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디는 회원이 아닙니다. loginID : " + loginId));
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시판은 존재하지 않습니다. boardId : " + boardId));
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        if (comment.getBoard() != board) {
            String message = String.format("해당 경로는 맞지 않습니다. url : api/boards/%s/comments/%s ", boardId, commentId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }

        return comment;
    }
}
