package org.sparta.newsfeed.domain.user.service;

import org.sparta.newsfeed.domain.user.dto.*;

public interface UserService {

    SignupResponseDto signup (SignupRequestDto requestDto);

    LoginResponseDto login(LoginRequestDto requestDto);

    UserResponseDto findUserById(Long userId);

    UserResponseDto updateUser(Long userId, UpdateRequestDto requestDto);

    void deleteUser(Long userId, DeleteUserRequestDto requestDto);
}
