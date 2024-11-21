package org.sparta.newsfeed.comment;

import lombok.RequiredArgsConstructor;
import org.sparta.newsfeed.comment.dto.CommentResponseDto;
import org.sparta.newsfeed.entity.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<CommentResponseDto> getComments(Long boardId) {
        List<Comment> commentsByBoardId = commentRepository.findCommentsByBoardId(boardId);

        return commentsByBoardId.stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }
}
