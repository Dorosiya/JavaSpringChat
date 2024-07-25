package portfolio.chatApplication.security.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import portfolio.chatApplication.member.entity.Member;
import portfolio.chatApplication.member.exception.MemberNotFoundException;
import portfolio.chatApplication.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member memberData = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("사용자를 찾을 수 없습니다."));

        if (memberData != null) {
            return new CustomUserDetails(memberData);
        }

        return null;
    }

}
