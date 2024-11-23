package org.sparta.newsfeed.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.domain.board.dto.BoardRequestDto;
import org.sparta.newsfeed.domain.board.dto.BoardResponseDto;
import org.sparta.newsfeed.domain.board.dto.BoardPageResponseDto;
import org.sparta.newsfeed.domain.board.repository.BoardRepository;
import org.sparta.newsfeed.domain.user.repository.UserRepository;
import org.sparta.newsfeed.global.entity.Board;
import org.sparta.newsfeed.global.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.sparta.newsfeed.domain.friend.service.impl.FriendServiceImpl.getTargetsFriends;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, Long userId) {

        User user = userRepository.findByIdOrElseThrow(userId);

        Board board = Board.from(requestDto);

        board.setUser(user);

        Board savedboard = boardRepository.save(board);
        return savedboard.toDto();
    }

    //전체 목록 조회
    public List<BoardResponseDto> getBoardList() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(Board::toDto)
                .collect(Collectors.toList());
    }

    //페이징 적용 조회
    public BoardPageResponseDto getBoardListWithPaging(int page, int size, String criteria) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, criteria));
        Page<Board> boards = boardRepository.findAll(pageable);
        return new BoardPageResponseDto(boards);
    }

    //페이징 적용 친구 게시글 조회
    public List<BoardResponseDto> getFriendsBoardList(int page, int size, String criteria, Long userId) {

        String sort = "modified_at";

        if(!criteria.equals("ModifiedAt"))
            sort = "";

        List<Board> boards = boardRepository.findFriendsBoardList(userId, sort, size, page*size);

        return boards.stream()
                .map(board -> new BoardResponseDto(
                        board.getId(),
                        board.getPostImage(),
                        board.getPostBody(),
                        (long) board.getBoardLike().size(),
                        board.getModifiedAt()
                ))
            .toList();

    }

    public BoardResponseDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found with id: " + boardId));
        return board.toDto();
    }

    @Transactional //영속성 안에서 엔티티가 바뀌면 자동저장
    public void updateBoard(Long boardId, BoardRequestDto requestDto) {
        Board board = boardRepository.findBoardById(boardId);
        board.updateData(requestDto);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.findBoardById(boardId);
        boardRepository.deleteById(boardId);
    }
}
