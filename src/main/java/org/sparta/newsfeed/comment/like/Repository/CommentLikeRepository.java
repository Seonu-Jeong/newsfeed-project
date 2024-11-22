package org.sparta.newsfeed.comment.like.Repository;

import org.sparta.newsfeed.entity.Comment;
import org.sparta.newsfeed.entity.CommentLike;
import org.sparta.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUserAndComment(User user, Comment comment);

    Optional<CommentLike> findByUserAndComment(User loginUser, Comment comment);
}
