package org.sparta.newsfeed.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity{

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
    private String comment;

    @Column(length = 20)
    private String status;

    public User () {
    }

    public User (String email, String password, String nickname) {
       this.email = email;
       this.password = password;
       this.nickname = nickname;
    }

}
