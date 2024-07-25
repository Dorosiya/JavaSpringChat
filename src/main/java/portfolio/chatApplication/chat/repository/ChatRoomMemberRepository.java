package portfolio.chatApplication.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.chatApplication.chat.entity.ChatRoom;
import portfolio.chatApplication.chat.entity.ChatRoomMember;
import portfolio.chatApplication.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    List<ChatRoomMember> findByMember(Member member);
    List<ChatRoomMember> findByChatRoom(ChatRoom chatRoom);
    Optional<ChatRoomMember> findByChatRoomAndMember(ChatRoom chatRoom, Member member);
}
