package portfolio.chatApplication.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PageController {

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/users")
    public String userList(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "users";
    }

    @GetMapping("/chat")
    public String chat(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "chat";
    }

}
