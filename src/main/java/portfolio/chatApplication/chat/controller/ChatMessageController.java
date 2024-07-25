package portfolio.chatApplication.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import portfolio.chatApplication.chat.dto.request.ChatMessageDto;
import portfolio.chatApplication.chat.entity.ChatMessage;
import portfolio.chatApplication.chat.service.ChatMessageService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDto chatMessageDto) {
        log.info("받은 메시지: {}", chatMessageDto);

        // roomId 유효성 검사
        if (chatMessageDto.getRoomId() == null || chatMessageDto.getRoomId().isEmpty()) {
            throw new IllegalArgumentException("채팅방 ID는 null 이거나 비어 있을 수 없습니다");
        }

        // 메시지 처리
        ChatMessage chatMessage = chatMessageService.saveMessage(chatMessageDto.getRoomId(), chatMessageDto.getMemberId(), chatMessageDto.getContent());
        log.info("저장된 메시지: {}", chatMessage);

        // 응답 DTO 생성
        ChatMessageDto responseDto = new ChatMessageDto();
        responseDto.setContent(chatMessage.getMessage());
        responseDto.setMemberId(chatMessage.getMemberId());
        responseDto.setRoomId(chatMessage.getChatRoom().getRoomId());
        log.info("응답 전송: {}", responseDto);

        // 메시지를 특정 주제로 전송
        messagingTemplate.convertAndSend("/topic/" + chatMessageDto.getRoomId(), responseDto);
    }

    @GetMapping("/room/{roomId}/messages")
    public List<ChatMessageDto> getMessages(@PathVariable String roomId) {
        List<ChatMessage> messages = chatMessageService.getMessagesByRoomId(roomId);
        return messages.stream().map(ChatMessageDto::fromEntity).collect(Collectors.toList());
    }

}
