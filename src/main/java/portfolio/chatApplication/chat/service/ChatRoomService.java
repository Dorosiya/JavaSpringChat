package portfolio.chatApplication.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import portfolio.chatApplication.chat.dto.response.ChatRoomMembersResponse;
import portfolio.chatApplication.chat.entity.ChatRoom;
import portfolio.chatApplication.chat.entity.ChatRoomMember;
import portfolio.chatApplication.chat.repository.ChatRoomMemberRepository;
import portfolio.chatApplication.chat.repository.ChatRoomRepository;
import portfolio.chatApplication.member.entity.Member;
import portfolio.chatApplication.member.exception.MemberNotFoundException;
import portfolio.chatApplication.member.repository.MemberRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomMemberRepository chatRoomMemberRepository;

    private final MemberRepository memberRepository;

    public ChatRoom createOrGetChatRoom(String senderUsername, String receiverUsername) {
        Member sender = memberRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new MemberNotFoundException("사용자를 찾을 수 없습니다. 유저네임 : " + senderUsername));
        Member receiver = memberRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new MemberNotFoundException("사용자를 찾을 수 없습니다. 유저네임 : " + receiverUsername));

        List<ChatRoomMember> senderRooms = chatRoomMemberRepository.findByMember(sender);
        for (ChatRoomMember member : senderRooms) {
            if (chatRoomMemberRepository.findByChatRoomAndMember(member.getChatRoom(), receiver).isPresent()) {
                return member.getChatRoom();
            }
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .build();
        chatRoom = chatRoomRepository.save(chatRoom);

        ChatRoomMember chatRoomMember1 = ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .member(sender)
                .build();
        chatRoomMemberRepository.save(chatRoomMember1);

        ChatRoomMember chatRoomMember2 = ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .member(receiver)
                .build();
        chatRoomMemberRepository.save(chatRoomMember2);

        return chatRoom;
    }

    public ChatRoomMembersResponse getChatRoomMembers(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 채팅방 ID 입니다."));

        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findByChatRoom(chatRoom);

        Map<Long, String> members = chatRoomMembers.stream()
                .collect(Collectors.toMap(
                        member -> member.getMember().getMemberId(),
                        member -> member.getMember().getUsername()
                ));

        ChatRoomMembersResponse response = new ChatRoomMembersResponse();
        response.setMembers(members);

        return response;
    }

    public Optional<ChatRoom> getChatRoom(String roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }

}
