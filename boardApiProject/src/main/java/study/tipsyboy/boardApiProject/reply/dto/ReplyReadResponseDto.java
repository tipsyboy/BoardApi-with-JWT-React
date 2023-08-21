package study.tipsyboy.boardApiProject.reply.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.tipsyboy.boardApiProject.reply.domain.Reply;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReplyReadResponseDto {

    private Long replyId;
    private String content;
    private LocalDateTime createDate;

    public static ReplyReadResponseDto from(Reply entity) {
        return new ReplyReadResponseDto(
                entity.getId(),
                entity.getContent(),
                entity.getCreateDate());
    }
}