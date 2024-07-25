package portfolio.chatApplication.member.dto.request;

import lombok.Data;

@Data
public class LoginDto {

    private String username;

    private String password;

    private String status;

}
