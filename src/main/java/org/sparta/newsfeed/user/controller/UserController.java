package org.sparta.newsfeed.user.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.sparta.newsfeed.constant.Const;
import org.sparta.newsfeed.user.dto.*;
import org.sparta.newsfeed.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup (@Valid @RequestBody SignupRequestDto requestDto) {

        SignupResponseDto signupResponseDto = userService.signup(requestDto.getEmail(), requestDto.getPassword(), requestDto.getNickname());

        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);

    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto requestdto, HttpServletRequest servletRequest) {

        //이메일, 비밀번호 확인
        LoginResponseDto loginDto = userService.login(requestdto.getEmail(), requestdto.getPassword());

        //세션요청
        HttpSession session = servletRequest.getSession();

        //세션 키 & Value 설정
        session.setAttribute(Const.LOGIN_USER, loginDto.getUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
