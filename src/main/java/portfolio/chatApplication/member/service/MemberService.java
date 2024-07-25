package portfolio.chatApplication.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.chatApplication.member.dto.request.JoinDto;
import portfolio.chatApplication.member.dto.response.MemberDto;
import portfolio.chatApplication.member.entity.Member;
import portfolio.chatApplication.member.exception.DuplicationException;
import portfolio.chatApplication.member.exception.MemberNotFoundException;
import portfolio.chatApplication.member.repository.MemberRepository;
import portfolio.chatApplication.role.entity.Role;
import portfolio.chatApplication.role.enums.RoleType;
import portfolio.chatApplication.role.repository.RoleRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Transactional
    public void registerMember(JoinDto joinDto) {

        String username = joinDto.getUsername();
        String password = joinDto.getPassword();
        String email = joinDto.getEmail();
        int age = joinDto.getAge();

        Boolean isExist = memberRepository.existsByUsername(username);

        if (isExist) {
            throw new DuplicationException("사용할 수 없는 Username 입니다. 유저네임 : " + username);
        }

        Role userRole = roleRepository.findByRoleName(RoleType.ROLE_USER)
                .orElseThrow(() -> new IllegalArgumentException("해당 권한을 찾을 수 없습니다."));

        Member joinMember = Member.builder()
                .role(userRole)
                .username(username)
                .password(encoder.encode(password))
                .age(age)
                .email(email)
                .build();

        memberRepository.save(joinMember);
    }

    @Transactional(readOnly = true)
    public Member getMember(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("사용자를 찾을 수 없습니다. 유저네임 : " + username));
    }

    @Transactional(readOnly = true)
    public MemberDto getMemberDto(String username) {
        Member findMember = getMember(username);

        return MemberDto.builder()
                .memberId(findMember.getMemberId())
                .username(findMember.getUsername())
                .age(findMember.getAge())
                .email(findMember.getEmail())
                .build();
    }

    @Transactional(readOnly = true)
    public List<MemberDto> getMembersDto(Long myMemberId) {
        return memberRepository.getMembers(myMemberId);
    }

}
