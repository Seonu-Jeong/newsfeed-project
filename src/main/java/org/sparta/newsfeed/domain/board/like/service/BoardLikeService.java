package org.sparta.newsfeed.domain.board.like.service;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.domain.board.like.repository.BoardLikeRepository;
import org.sparta.newsfeed.domain.board.repository.BoardRepository;
import org.sparta.newsfeed.domain.comment.repository.CommentRepository;
import org.sparta.newsfeed.domain.user.repository.UserRepository;
import org.sparta.newsfeed.global.entity.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public void addLike(Long boardId,Long loginId) {
        User loginUser = userRepository.findByIdOrElseThrow(loginId);
        Board board = boardRepository.findBoardById(boardId);

        if (!boardLikeRepository.existsByUserAndBoard(loginUser, board)) {
            BoardLike boardLike = new BoardLike(loginUser, board);
            boardLikeRepository.save(boardLike);
        }
    }

    public void deleteLike(Long loginId, Long boardId) {
        User loginUser = userRepository.findByIdOrElseThrow(loginId);
        Board board = boardRepository.findBoardById(boardId);

        Optional<BoardLike> findByUserAndBoard = boardLikeRepository.findByUserAndBoard(loginUser, board);
        if(findByUserAndBoard.isPresent()) {
            BoardLike boardLike = findByUserAndBoard.get();
            boardLikeRepository.delete(boardLike);
        }
    }
}
