package org.sparta.newsfeed.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.sparta.newsfeed.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class UpdateRequestDto {

    @NotNull
    @Size(max=20)
    private String nickname;

    private String userImage;

    @Size(max=30)
    private String selfComment;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "비밀번호 형식이 올바르지 않습니다. 8자 이상, 대소문자 포함, 숫자 및 특수문자(@$!%*?&#) 포함")
    private String oldPassword;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "비밀번호 형식이 올바르지 않습니다. 8자 이상, 대소문자 포함, 숫자 및 특수문자(@$!%*?&#) 포함")
    private String newPassword;

    public static void isSamePassword (UpdateRequestDto dto) {
        if (dto.getOldPassword().equals(dto.getNewPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "새로운 비밀번호는 기존 비밀번호와 중복될 수 없습니다.");
        }
    }

}
