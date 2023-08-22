package study.tipsyboy.boardApiProject.reply.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReplyUpdateRequestDto {

    private Long replyId;
    private String content;
}
