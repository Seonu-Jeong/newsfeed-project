package org.sparta.newsfeed.user.controller;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.user.dto.SignupRequestDto;
import org.sparta.newsfeed.user.dto.SignupResponseDto;
import org.sparta.newsfeed.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public ResponseEntity<SignupResponseDto> signup (@RequestBody SignupRequestDto requestDto) {

        SignupResponseDto signupResponseDto = userService.signup(requestDto.getEmail(), requestDto.getPassword(), requestDto.getNickname());

        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);

    }
}
