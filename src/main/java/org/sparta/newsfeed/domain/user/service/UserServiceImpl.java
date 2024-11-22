package org.sparta.newsfeed.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.global.config.PasswordEncoder;
import org.sparta.newsfeed.domain.user.dto.*;
import org.sparta.newsfeed.global.entity.User;
import org.sparta.newsfeed.user.dto.*;
import org.sparta.newsfeed.domain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.sparta.newsfeed.domain.user.dto.UpdateRequestDto.isSamePassword;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignupResponseDto signup(SignupRequestDto requestDto) {

        if (userRepository.countByEmail(requestDto.getEmail()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getEmail(), encodedPassword, requestDto.getNickname());

        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser.getEmail(), savedUser.getNickname());

    }

    public LoginResponseDto login(LoginRequestDto requestDto) {

        User loginUser = userRepository.findByEmailOrElseThrow(requestDto.getEmail());

        if (!passwordEncoder.matches(requestDto.getPassword(), loginUser.getPassword())) {
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

        if (requestDto.getOldPassword() == null && requestDto.getNewPassword() == null) {

            return new UserResponseDto(user);
        }

        else if (requestDto.getOldPassword() != null && requestDto.getNewPassword() != null) {

            isSamePassword(requestDto);     //기존 비밀번호와 새 비밀번호 비교

            //oldPassword 를 데이터의 password 와 비교한 후 newPassword 를 encode 하여 변경
            if (passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {

                user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return new UserResponseDto(user);
    }

    @Override
    public void deleteUser(Long userId, DeleteUserRequestDto requestDto) {

        User user = userRepository.findByIdOrElseThrow(userId);

        if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            userRepository.delete(user);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong Password.");
        }

    }


}
