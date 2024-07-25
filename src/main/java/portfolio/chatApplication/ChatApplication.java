package portfolio.chatApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import portfolio.chatApplication.common.util.SpringSecurityAuditorAware;

@EnableJpaAuditing
@SpringBootApplication
public class ChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new SpringSecurityAuditorAware();
	}
}
