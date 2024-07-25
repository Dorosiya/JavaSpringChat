package portfolio.chatApplication.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.chatApplication.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {


}
