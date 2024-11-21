package org.sparta.newsfeed.user.service;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.config.PasswordEncoder;
import org.sparta.newsfeed.entity.User;
import org.sparta.newsfeed.user.dto.LoginResponseDto;
import org.sparta.newsfeed.user.dto.SignupResponseDto;
import org.sparta.newsfeed.user.dto.UpdateRequestDto;
import org.sparta.newsfeed.user.dto.UserResponseDto;
import org.sparta.newsfeed.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignupResponseDto signup(String email, String password, String nickname) {

        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(email, encodedPassword, nickname);

        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser.getEmail(), savedUser.getNickname());

    }

    public LoginResponseDto login(String email, String password) {

        User loginUser = userRepository.findByEmailOrElseThrow(email);

        if (!passwordEncoder.matches(password, loginUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호를 확인하세요.");
        }

        return new LoginResponseDto(loginUser.getId());
    }

    public UserResponseDto findUserById(Long userId) {

        User foundUser = userRepository.findByIdOrElseThrow(userId);

        return new UserResponseDto(foundUser);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long userId, UpdateRequestDto requestDto) {

        User user = userRepository.findByIdOrElseThrow(userId);

        user.setNickname(requestDto.getNickname());
        user.setUserImage(requestDto.getUserImage());
        user.setSelfComment(requestDto.getSelfComment());

        //비밀번호 수정 -> oldPassword, newPassword 는 둘다 입력될 경우에만 수정
        if (requestDto.getOldPassword() != null && requestDto.getNewPassword() != null) {
            //oldPassword 를 데이터의 password 와 비교한 후 newPassword를 encode 하여 변경
            if (passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } else if (requestDto.getOldPassword() == null && requestDto.getNewPassword() == null) {
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return new UserResponseDto(user);
    }


}
