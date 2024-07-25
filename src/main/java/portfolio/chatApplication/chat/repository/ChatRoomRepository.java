package portfolio.chatApplication.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.chatApplication.chat.entity.ChatRoom;
import portfolio.chatApplication.chat.entity.ChatRoomMember;
import portfolio.chatApplication.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByRoomId(String roomId);

}
