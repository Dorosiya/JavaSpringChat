package portfolio.chatApplication.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolio.chatApplication.chat.dto.request.ChatRoomRequest;
import portfolio.chatApplication.chat.dto.response.ChatRoomMembersResponse;
import portfolio.chatApplication.chat.entity.ChatRoom;
import portfolio.chatApplication.chat.service.ChatRoomService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/room")
    public ChatRoom createOrGetChatRoom(@RequestBody ChatRoomRequest request) {
        return chatRoomService.createOrGetChatRoom(request.getSender(), request.getReceiver());
    }

    @GetMapping("/room/{roomId}/members")
    public ResponseEntity<ChatRoomMembersResponse> getChatRoomMembers(@PathVariable String roomId) {
        ChatRoomMembersResponse response = chatRoomService.getChatRoomMembers(roomId);
        return ResponseEntity.ok(response);
    }

    /*@GetMapping("/room/{roomId}")
    public ChatRoom getChatRoom(@PathVariable String roomId) {
        return chatRoomService.getChatRoom(roomId).orElse(null);
    }*/

}
