package org.sparta.newsfeed.global.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "board_like")
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BoardLike() {
    }

    public BoardLike(User user, Board board) {
        this.board = board;
        this.user = user;
    }
}
