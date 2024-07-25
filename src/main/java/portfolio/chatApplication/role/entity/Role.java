package portfolio.chatApplication.role.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import portfolio.chatApplication.common.entity.BaseEntity;
import portfolio.chatApplication.role.enums.RoleType;

@Getter
@NoArgsConstructor
@Table(name = "role")
@Entity
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    private RoleType roleName;

    @Builder
    private Role(RoleType roleName) {
        this.roleName = roleName;
    }

}
