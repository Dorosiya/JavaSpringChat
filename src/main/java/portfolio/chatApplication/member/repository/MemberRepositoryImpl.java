package portfolio.chatApplication.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import portfolio.chatApplication.member.dto.response.MemberDto;
import portfolio.chatApplication.member.entity.Member;

import java.util.List;
import java.util.Optional;

import static portfolio.chatApplication.member.entity.QMember.member;
import static portfolio.chatApplication.role.entity.QRole.role;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .leftJoin(member.role, role).fetchJoin()
                .where(member.username.eq(username))
                .fetchOne();
        return Optional.ofNullable(findMember);
    }

    @Override
    public Boolean existsByUsername(String username) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(member)
                .where(member.username.eq(username))
                .fetchOne();

        return fetchOne != null ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public List<MemberDto> getMembers(Long myMemberId) {
        return queryFactory
                .select(Projections.constructor(MemberDto.class,
                        member.memberId,
                        member.username))
                .from(member)
                .where(member.memberId.ne(myMemberId))
                .fetch();
    }
}
