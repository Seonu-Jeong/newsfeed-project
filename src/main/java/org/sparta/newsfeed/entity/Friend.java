package org.sparta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.sparta.newsfeed.entity.enums.FriendType;

@Getter
@Entity
@Table(name = "friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "posted_user_id")
    private User postedUser;

    @OneToOne
    @JoinColumn(name = "requested_user_id")
    private User requestedUser;

    @Enumerated(EnumType.STRING)
    private FriendType state;
}