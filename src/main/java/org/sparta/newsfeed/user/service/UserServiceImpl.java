package org.sparta.newsfeed.user.service;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.config.PasswordEncoder;
import org.sparta.newsfeed.entity.User;
import org.sparta.newsfeed.user.dto.LoginResponseDto;
import org.sparta.newsfeed.user.dto.SignupResponseDto;
import org.sparta.newsfeed.user.dto.UserResponseDto;
import org.sparta.newsfeed.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignupResponseDto signup (String email, String password, String nickname) {

        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(email, encodedPassword, nickname);

        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser.getEmail(), savedUser.getNickname());

    }

    public LoginResponseDto login (String email, String password) {

        User loginUser = userRepository.findByEmailOrElseThrow(email);

        if (!passwordEncoder.matches(password, loginUser.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호를 확인하세요.");
        }

        return new LoginResponseDto(loginUser.getId());
    }

    public UserResponseDto findUserById(Long userId){

        User foundUser = userRepository.findByIdOrElseThrow(userId);

        return new UserResponseDto(
                foundUser.getNickname(),
                foundUser.getUserImage(),
                foundUser.getSelfComment(),
                (long) foundUser.getBoards().size(),
                (long) (foundUser.getRequestedUsers().size() + foundUser.getPostedUsers().size()));
    }
}
