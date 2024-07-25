package portfolio.chatApplication.chat.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class ChatRoomMembersResponse {
    private Map<Long, String> members;
}
