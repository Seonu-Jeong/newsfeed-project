package org.sparta.newsfeed.user.service;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.entity.User;
import org.sparta.newsfeed.user.dto.SignupResponseDto;
import org.sparta.newsfeed.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SignupResponseDto signup (String email, String password, String nickname) {

        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다.");
        }

        User user = new User(email, password, nickname);

        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser.getEmail(), savedUser.getNickname());

    }

}
