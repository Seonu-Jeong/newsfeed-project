package org.sparta.newsfeed.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "commentLike")
public class CommentLike extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CommentLike() {
    }

    public CommentLike(User user, Comment comment) {
        this.comment = comment;
        this.user = user;
    }
}