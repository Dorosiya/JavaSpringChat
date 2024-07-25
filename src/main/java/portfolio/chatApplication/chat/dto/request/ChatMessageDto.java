package portfolio.chatApplication.chat.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import portfolio.chatApplication.chat.entity.ChatMessage;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDto {
    private String content;
    private Long memberId;
    private String roomId;

    public static ChatMessageDto fromEntity(ChatMessage chatMessage) {
        ChatMessageDto dto = new ChatMessageDto();
        dto.setContent(chatMessage.getMessage());
        dto.setMemberId(chatMessage.getMemberId());
        dto.setRoomId(chatMessage.getChatRoom().getRoomId());
        return dto;
    }
}
