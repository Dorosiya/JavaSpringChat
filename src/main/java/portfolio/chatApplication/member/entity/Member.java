package portfolio.chatApplication.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import portfolio.chatApplication.chat.entity.ChatRoomMember;
import portfolio.chatApplication.common.entity.BaseEntity;
import portfolio.chatApplication.role.entity.Role;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_Id", updatable = false)
    private Role role;

    @Column(unique = true)
    private String username;

    private String password;

    private int age;

    private String email;

    @Builder
    private Member(Long memberId, Role role, String username, String password, int age, String email) {
        this.memberId = memberId;
        this.role = role;
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
    }

}
