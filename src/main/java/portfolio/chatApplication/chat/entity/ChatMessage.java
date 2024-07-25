package portfolio.chatApplication.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import portfolio.chatApplication.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    private Long memberId;
    private String message;

    @Builder
    private ChatMessage(ChatRoom chatRoom, Long memberId, String message) {
        this.chatRoom = chatRoom;
        this.memberId = memberId;
        this.message = message;
    }
}
