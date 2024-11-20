package org.sparta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "board")

public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "tinytext")
    private String postBody;

    @Column(columnDefinition = "tinytext")
    private String postImage;

    public Board(){
    }

    public Board(String postBody, String postImage) {
        this.postBody = postBody;
        this.postImage = postImage;

    }
}
