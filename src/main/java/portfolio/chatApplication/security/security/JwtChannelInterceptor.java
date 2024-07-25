package portfolio.chatApplication.security.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import portfolio.chatApplication.security.jwt.JwtUtil;

import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_ = "Bearer ";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Optional<String> jwtTokenOptional = Optional.ofNullable(accessor.getFirstNativeHeader(AUTHORIZATION));
            String jwtToken = jwtTokenOptional
                    .filter(token -> token.startsWith(BEARER_))
                    .map(token -> token.substring(BEARER_.length()))
                    .filter(jwtUtil::validateToken)
                    .orElseThrow(() -> new AccessDeniedException("Invalid token"));

            String username = jwtUtil.getUsername(jwtToken);
            String role = jwtUtil.getRole(jwtToken);

            GrantedAuthority authority = new SimpleGrantedAuthority(role);

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, List.of(authority));
            accessor.setUser(authentication);
        }

        return message;
    }
}
