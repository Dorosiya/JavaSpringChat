package portfolio.chatApplication.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import portfolio.chatApplication.chat.entity.ChatMessage;
import portfolio.chatApplication.chat.entity.ChatRoom;
import portfolio.chatApplication.chat.repository.ChatMessageRepository;
import portfolio.chatApplication.chat.repository.ChatRoomRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage saveMessage(String roomId, Long memberId, String message) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 채팅방 ID 입니다."));
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .memberId(memberId)
                .message(message)
                .build();
        chatMessage = chatMessageRepository.save(chatMessage);
        log.info("저장된 채팅 메세지: {}", chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> getMessagesByRoomId(String roomId) {
        return chatMessageRepository.findByChatRoomRoomId(roomId);
    }
}
