package org.sparta.newsfeed.user.repository;

import org.sparta.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail (String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    default User findByEmailAndPasswordOrElse(String email, String password) {
        return findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인할 수 없습니다."));
    };


}
