package org.sparta.newsfeed.user.service;

import org.sparta.newsfeed.user.dto.LoginResponseDto;
import org.sparta.newsfeed.user.dto.SignupResponseDto;
import org.sparta.newsfeed.user.dto.UserResponseDto;

public interface UserService {

    SignupResponseDto signup (String email, String password, String nickname);

    LoginResponseDto login(String email, String password);

    UserResponseDto findUserById(Long userId);

}
