package portfolio.chatApplication.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import portfolio.chatApplication.member.dto.request.JoinDto;
import portfolio.chatApplication.member.dto.response.MemberDto;
import portfolio.chatApplication.member.service.MemberService;
import portfolio.chatApplication.security.security.CustomUserDetails;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<Map<String, ?>> registerMember(@Valid @RequestBody JoinDto joinDto) {
        log.info("Post /member 시작");
        memberService.registerMember(joinDto);

        return ResponseEntity.ok(Map.of("success", true, "message", "Registration successful"));
    }

    @GetMapping("/member")
    public ResponseEntity<MemberDto> getMember(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("Get /member 시작");
        MemberDto memberDto = memberService.getMemberDto(customUserDetails.getUsername());

        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberDto>> getMembers(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("Get /members 시작");
        Long myMemberId = customUserDetails.getMemberId();
        List<MemberDto> membersDto = memberService.getMembersDto(myMemberId);

        return new ResponseEntity<>(membersDto, HttpStatus.OK);
    }

}
