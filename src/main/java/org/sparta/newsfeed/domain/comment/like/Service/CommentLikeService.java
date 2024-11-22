package org.sparta.newsfeed.domain.comment.like.Service;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.domain.board.repository.BoardRepository;
import org.sparta.newsfeed.domain.comment.like.Repository.CommentLikeRepository;
import org.sparta.newsfeed.domain.comment.repository.CommentRepository;
import org.sparta.newsfeed.global.entity.Comment;
import org.sparta.newsfeed.global.entity.CommentLike;
import org.sparta.newsfeed.global.entity.User;
import org.sparta.newsfeed.domain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public void addLike(Long loginId, Long boardId, Long commentId) {
        User loginUser = userRepository.findByIdOrElseThrow(loginId);
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        AuthorizedPath(boardId, comment);

        if (!commentLikeRepository.existsByUserAndComment(loginUser, comment)) {
            CommentLike commentLike = new CommentLike(loginUser, comment);
            commentLikeRepository.save(commentLike);
        }
    }

    public void deleteLike(Long loginId, Long boardId, Long commentId) {
        User loginUser = userRepository.findByIdOrElseThrow(loginId);
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        AuthorizedPath(boardId, comment);

        Optional<CommentLike> byUserAndComment = commentLikeRepository.findByUserAndComment(loginUser, comment);
        if(byUserAndComment.isPresent()) {
            CommentLike commentLike = byUserAndComment.get();
            commentLikeRepository.delete(commentLike);
        }
    }

    private static void AuthorizedPath(Long boardId, Comment comment) {
        if (!comment.getBoard().getId().equals(boardId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글의 경로가 맞지 않습니다. boardID : " + boardId);
        }
    }
}
