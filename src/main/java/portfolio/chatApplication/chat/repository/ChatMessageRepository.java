package portfolio.chatApplication.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.chatApplication.chat.entity.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomRoomId(String roomId);
}
