package org.sparta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.newsfeed.entity.enums.FriendType;

@Setter
@NoArgsConstructor
@Getter
@Entity
@Table(name = "friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "posted_user_id")
    private User postedUser;

    @ManyToOne
    @JoinColumn(name = "requested_user_id")
    private User requestedUser;

    @Enumerated(EnumType.STRING)
    private FriendType state;

    public Friend(User postedUser, User requestedUser, FriendType state) {
        this.postedUser = postedUser;
        this.requestedUser = requestedUser;
        this.state = state;
    }
}