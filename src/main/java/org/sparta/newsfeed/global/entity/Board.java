package org.sparta.newsfeed.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.newsfeed.domain.board.dto.BoardRequestDto;
import org.sparta.newsfeed.domain.board.dto.BoardResponseDto;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "board")

public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(columnDefinition = "tinytext")
    private String postBody;

    @Column(columnDefinition = "tinytext")
    private String postImage;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public static Board from(BoardRequestDto requestDto) {
        Board board = new Board();
        board.initData(requestDto);
        return board;
    }

    private void initData(BoardRequestDto requestDto) {
        this.postBody = requestDto.getPostBody();
        this.postImage = requestDto.getPostImage();
    }

    public BoardResponseDto to() {
        return new BoardResponseDto(
                id,
                postImage,
                postBody,
                getCreatedAt()
        );
    }

    public void updateData(BoardRequestDto requestDto) {
        this.postImage= requestDto.getPostImage();
        this.postBody = requestDto.getPostBody();

    }
}
