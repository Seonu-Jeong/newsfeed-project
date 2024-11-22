package org.sparta.newsfeed.friend.repository;

import org.sparta.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReopository extends JpaRepository<User, Long> {

}
