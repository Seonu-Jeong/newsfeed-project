package org.sparta.newsfeed.domain.comment.like.Repository;

import org.sparta.newsfeed.global.entity.Comment;
import org.sparta.newsfeed.global.entity.CommentLike;
import org.sparta.newsfeed.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUserAndComment(User user, Comment comment);

    Optional<CommentLike> findByUserAndComment(User loginUser, Comment comment);
}
