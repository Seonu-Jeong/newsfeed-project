package org.sparta.newsfeed.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.global.constant.Const;
import org.sparta.newsfeed.domain.user.dto.*;
import org.sparta.newsfeed.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto, HttpServletRequest servletRequest) {

        if (servletRequest.getSession(false) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        SignupResponseDto signupResponseDto = userService.signup(requestDto);

        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletRequest servletRequest) {

        if (servletRequest.getSession(false) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //이메일, 비밀번호 확인
        LoginResponseDto loginDto = userService.login(requestDto);

        //세션요청
        HttpSession session = servletRequest.getSession();



        //세션 키 & Value 설정
        session.setAttribute(Const.LOGIN_USER, loginDto.getUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest servletRequest) {

        HttpSession session = servletRequest.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //유저 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long userId) {

        UserResponseDto foundUserDto = userService.findUserById(userId);

        return new ResponseEntity<>(foundUserDto, HttpStatus.OK);
    }

    //유저 수정
    @PatchMapping("/modify")
    public ResponseEntity<UserResponseDto> updateUser(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Valid @RequestBody UpdateRequestDto requestDto
    ) {
        UserResponseDto updateUserDto = userService.updateUser(userId, requestDto);

        return new ResponseEntity<>(updateUserDto, HttpStatus.OK);
    }

    //회원탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> deleteUser(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Valid @RequestBody DeleteUserRequestDto requestDto,
            HttpServletRequest servletRequest
    ) {
        userService.deleteUser(userId, requestDto);

        //세션 로그아웃
        HttpSession session = servletRequest.getSession(false);
        session.invalidate();

        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
