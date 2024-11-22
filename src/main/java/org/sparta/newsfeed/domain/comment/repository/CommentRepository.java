package org.sparta.newsfeed.domain.comment.repository;

import org.sparta.newsfeed.global.entity.Board;
import org.sparta.newsfeed.global.entity.Comment;
import org.sparta.newsfeed.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByBoardId(Long board_id);

    default Comment findByIdOrElseThrow(Long commentId) {
        return findById(commentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글을 찾지 못했습니다 commentId : " + commentId)
        );
    }
}