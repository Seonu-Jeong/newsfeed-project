package org.sparta.newsfeed.domain.friend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.global.constant.Const;
import org.sparta.newsfeed.domain.friend.dto.ErrorResponseDto;
import org.sparta.newsfeed.domain.friend.dto.FriendRelationResponseDto;
import org.sparta.newsfeed.domain.friend.dto.FriendResponseDto;
import org.sparta.newsfeed.domain.friend.dto.RequestAcceptResponseDto;
import org.sparta.newsfeed.domain.friend.exception.AlreadyExistException;
import org.sparta.newsfeed.domain.friend.exception.NoAuthorizationException;
import org.sparta.newsfeed.domain.friend.exception.NoExistException;
import org.sparta.newsfeed.domain.friend.service.FriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/friends")
@RestController
public class FriendController {

    private final FriendService friendService;

    /**
     * 로그인 된 사용자와 요청한 사용자에 대한 친구 관계 목록을 가져오는 api 메소드.<br>
     * 추가로 친구 요청을 보낼 수 있도록 친구 관계에 대한 정보가 요구되는 것이 특징이다.
     *
     * @param targetId 검색하고자 하는 유저의 id
     * @return UserFriendDto 리스트
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{targetId}")
    public List<FriendRelationResponseDto> getUsersFriendList(
        @PathVariable Long targetId,
        HttpServletRequest request
    ){

        HttpSession session = request.getSession(false);

        Long loginUserId = (Long)session.getAttribute(Const.LOGIN_USER);

        return friendService.findUsersFriendList(targetId, loginUserId);
    }


    /**
     * 경로변수 userId를 가진 사용자에게 들어온 친구 요청 목록을 반환하는 api 메소드.<br>
     * 로그인 된 사용자와 동일한 userId를 가져야 한다.
     *
     * @param userId 검색할 사용자 아이디
     * @param request Http요청 객체
     * @return RequestFriendResponseDto 리스트
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/requests/{userId}")
    public List<FriendResponseDto> getRequestFriendList(
            @PathVariable Long userId,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);

        Long loginUserId = (Long)session.getAttribute(Const.LOGIN_USER);

        if(!userId.equals(loginUserId)){
            throw new NoAuthorizationException("로그인 사용자와 일치하지 않은 아이디입니다.");
        }

        return friendService.findRequestFriends(userId);
    }


    /**
     * 특정 사용자에게 친구 요청을 보내는 api 메소드
     *
     * @param targetId 요청하고자 하는 상대방 아이디
     * @param request Http 요청 객체
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/requests/{targetId}")
    public void requestFriend(
            @PathVariable Long targetId,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);

        Long loginUserId = (Long)session.getAttribute(Const.LOGIN_USER);

        friendService.sendFriendRequest(targetId, loginUserId);

    }


    /**
     *
     * 친구 요청을 수락하는 api 메소드
     *
     * @param targetId 친구 요청을 수락할 사용자 아이디
     * @param request Http 요청 객체
     * @return RequestAcceptResponseDto
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/requests/{targetId}")
    public RequestAcceptResponseDto acceptFriendRequest(
            @PathVariable Long targetId,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);

        Long loginUserId = (Long)session.getAttribute(Const.LOGIN_USER);

        return friendService.acceptRequest(targetId, loginUserId);
    }

    /**
     * 친구 요청 거절하는 api 메소드
     * 
     * @param targetId 친구 요청 거절할 사용자 아이디
     * @param request Http 요청 객체
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/requests/{targetId}")
    public void refuseFriendRequest(
            @PathVariable Long targetId,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);

        Long loginUserId = (Long)session.getAttribute(Const.LOGIN_USER);

        friendService.refuseRequset(targetId, loginUserId);
    }


    /**
     * 친구 관계 삭제 api 메소드
     *
     * @param targetId 삭제할 친구 아이디
     * @param request Http 요청 객체
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{targetId}")
    public void removeFriend(
            @PathVariable Long targetId,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);

        Long loginUserId = (Long)session.getAttribute(Const.LOGIN_USER);

        friendService.removeFriend(targetId, loginUserId);
    }

    @ExceptionHandler({NoAuthorizationException.class})
    public ResponseEntity<ErrorResponseDto> noAuthorizationExceptionHandler(Exception e){
        return new ResponseEntity<>(new ErrorResponseDto(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({NoExistException.class})
    public ResponseEntity<ErrorResponseDto> noExistExceptionHandler(Exception e){
        return new ResponseEntity<>(new ErrorResponseDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<ErrorResponseDto> alreadyExistExceptionHandler(Exception e){
        return new ResponseEntity<>(new ErrorResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
