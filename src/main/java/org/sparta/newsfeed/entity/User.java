package org.sparta.newsfeed.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 320, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String userImage;

    @Column(length = 30)
    private String selfComment;

    @Column(length = 20)
    private String status;

    //1:N 연관관계 -> 유저가 삭제될 경우 작성한 게시글이 모두 삭제
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    //1:N 연관관계 -> 유저가 삭제될 경우 작성한 댓글이 모두 삭제
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    //1:N 연관관계 -> 유저가 삭제될 경우 포함된 친구관계 모두 삭제
    @OneToMany(mappedBy = "postedUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Friend> postedUsers = new ArrayList<>();

    @OneToMany(mappedBy = "requestedUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Friend> requestedUsers = new ArrayList<>();

    //생성자
    public User() {
    }

    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

}
