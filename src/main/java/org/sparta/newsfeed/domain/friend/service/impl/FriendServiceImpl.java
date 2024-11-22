package org.sparta.newsfeed.domain.friend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.global.entity.Friend;
import org.sparta.newsfeed.global.entity.User;
import org.sparta.newsfeed.global.entity.enums.FriendType;
import org.sparta.newsfeed.domain.friend.dto.FriendRelationResponseDto;
import org.sparta.newsfeed.domain.friend.dto.FriendResponseDto;
import org.sparta.newsfeed.domain.friend.dto.RequestAcceptResponseDto;
import org.sparta.newsfeed.domain.friend.exception.AlreadyExistException;
import org.sparta.newsfeed.domain.friend.exception.NoExistException;
import org.sparta.newsfeed.domain.friend.repository.FriendRepository;
import org.sparta.newsfeed.domain.friend.repository.UserReopository;
import org.sparta.newsfeed.domain.friend.service.FriendService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements FriendService {

    private final UserReopository userRepository;

    private final FriendRepository friendRepository;

    @Transactional
    public List<FriendRelationResponseDto> findUsersFriendList(Long targetId, Long loginUserId) {
        User targetUser = userRepository.findById(targetId).orElseThrow(
                ()->new NoExistException("지정한 유저는 존재하지 않는 유저입니다.")
        );

        User loginUser = userRepository.findById(loginUserId).orElseThrow(
                ()->new NoExistException("로그인한 유저가 존재하지 않습니다.")
        );

        List<FriendResponseDto> friendResponseDtos = convertToFriendResponseDto(
                getTargetsFriends(targetUser).stream()
                        .filter(user-> !user.getId().equals(loginUserId))
                        .toList()
        );

        setRelatedFriendInfo(friendResponseDtos, loginUser);

        List<FriendRelationResponseDto> friendRelationResponseDtoLIst =
                friendResponseDtos.stream()
                        .map(friendResponseDto ->
                                new FriendRelationResponseDto(friendResponseDto))
                        .toList();

        setFriendWithMeRelation(friendRelationResponseDtoLIst, loginUser);

        return friendRelationResponseDtoLIst;
    }


    @Transactional
    public List<FriendResponseDto> findRequestFriends(Long userId) {
        User loginUser = userRepository.findById(userId).orElseThrow(
                ()->new NoExistException("로그인한 유저가 존재하지 않습니다.")
        );

        List<FriendResponseDto> postedFriendResponseDtos = convertToFriendResponseDto(
            friendRepository.findByRequestedUserId(userId).stream()
                    .filter(friend -> friend.getState() == FriendType.WAITING)
                    .map(Friend::getPostedUser)
                    .toList()
        );

        setRelatedFriendInfo(postedFriendResponseDtos, loginUser);

        return postedFriendResponseDtos;
    }


    @Transactional
    public void sendFriendRequest(Long targetId, Long loginUserId) {

        User targetUser = userRepository.findById(targetId).orElseThrow(
                ()->new NoExistException("지정한 유저가 존재하지 않습니다.")
        );

        User loginUser = userRepository.findById(loginUserId).orElseThrow(
                ()->new NoExistException("로그인 유저가 존재하지 않습니다.")
        );

        if(friendRepository.findByPostedUserIdAndRequestedUserId(loginUserId, targetId).isPresent() ||
                friendRepository.findByPostedUserIdAndRequestedUserId(targetId, loginUserId).isPresent()
        ){
            throw new AlreadyExistException("이미 관계가 존재 합니다.");
        }


        Friend friend = new Friend(loginUser, targetUser, FriendType.WAITING);
        friendRepository.save(friend);
    }

    @Transactional
    public RequestAcceptResponseDto acceptRequest(Long targetId, Long loginUserId) {

        Friend friend = friendRepository.findByPostedUserIdAndRequestedUserId(targetId, loginUserId)
                .orElseThrow(()->new NoExistException("존재하지 않는 요청입니다."));

        if(friend.getState()==FriendType.RELATION)
            throw new AlreadyExistException("이미 친구 요청을 보냈습니다.");

        friend.setState(FriendType.RELATION);

        return new RequestAcceptResponseDto(
                friend.getPostedUser().getNickname(), friend.getState()
        );
    }

    @Transactional
    public void refuseRequset(Long targetId, Long loginUserId) {

        Friend friend = friendRepository.findByPostedUserIdAndRequestedUserId(targetId, loginUserId)
                .orElseThrow(()->new NoExistException("존재하지 않는 요청입니다."));

        if(friend.getState()==FriendType.RELATION)
            throw new AlreadyExistException("이미 친구 관계입니다.");

        friendRepository.delete(friend);

    }

    @Transactional
    public void removeFriend(Long targetId, Long loginUserId) {

        Friend friend = friendRepository.findByPostedUserIdAndRequestedUserId(targetId, loginUserId)
                .orElse(null);

        if(friend==null)
            friend = friendRepository.findByPostedUserIdAndRequestedUserId(loginUserId, targetId)
                    .orElseThrow(()->new NoExistException("존재하지 친구 관계입니다."));

        if(friend.getState()!=FriendType.RELATION)
            throw new AlreadyExistException("친구 관계가 아닙니다.");

        friendRepository.delete(friend);

    }

    private void setRelatedFriendInfo(List<FriendResponseDto> dtos, User loginUser) {

        List<User> loginUserFriends = getTargetsFriends(loginUser);


        for(FriendResponseDto dto : dtos) {

            User targetUser = userRepository.findById(dto.getUserId()).get();

            List<User> targetUserFriends = getTargetsFriends(targetUser);

            targetUserFriends.retainAll(loginUserFriends);

            dto.setRelationFriendCount(0);
            dto.setRepresentFriendName("");

            if(!targetUserFriends.isEmpty()) {
                dto.setRepresentFriendName(targetUserFriends.get(0).getNickname());
                dto.setRelationFriendCount(targetUserFriends.size());
            }

        }

    }


    private void setFriendWithMeRelation(List<FriendRelationResponseDto> dtos, User loginUser) {

        for(FriendRelationResponseDto dto : dtos) {
            Long friendId = dto.getUserId();

            FriendType relation = FriendType.NO_RELATION;

            Friend friend = friendRepository.findByPostedUserIdAndRequestedUserId(friendId, loginUser.getId())
                    .orElse(null);

            if (friend != null) {
                relation = friend.getState();
            }

            friend = friendRepository.findByPostedUserIdAndRequestedUserId(loginUser.getId(), friendId)
                    .orElse(null);

            if (friend != null) {
                relation = friend.getState();
            }

            dto.setFriendStatus(relation);
        }
    }

    private  List<FriendResponseDto> convertToFriendResponseDto(List<User> userList) {

        return new ArrayList<>(userList.stream()
                .map(user ->
                        FriendResponseDto.builder()
                                .userId(user.getId())
                                .userName(user.getNickname())
                                .userImage(user.getUserImage())
                                .build()
                ).toList()
        );

    }

    private List<User> getTargetsFriends(User targetUser) {
        List<User> userList = new ArrayList<>();

        userList.addAll(
                targetUser.getPostedUsers().stream()
                        .filter(friend -> friend.getState() == FriendType.RELATION)
                        .map(Friend::getRequestedUser)
                        .toList()
        );

        userList.addAll(
                targetUser.getRequestedUsers().stream()
                        .filter(friend -> friend.getState() == FriendType.RELATION)
                        .map(Friend::getPostedUser)
                        .toList()
        );


        return userList;
    }


}

