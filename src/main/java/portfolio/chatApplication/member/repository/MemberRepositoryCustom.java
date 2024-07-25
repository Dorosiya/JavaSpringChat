package portfolio.chatApplication.member.repository;

import portfolio.chatApplication.member.dto.response.MemberDto;
import portfolio.chatApplication.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<Member> findByUsername(String username);

    Boolean existsByUsername(String username);

    List<MemberDto> getMembers(Long myMemberId);

}
