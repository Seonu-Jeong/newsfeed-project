package org.sparta.newsfeed.domain.friend.repository;

import org.sparta.newsfeed.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReopository extends JpaRepository<User, Long> {

}
