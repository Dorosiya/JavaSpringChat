package portfolio.chatApplication.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.chatApplication.role.entity.Role;
import portfolio.chatApplication.role.enums.RoleType;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleType roleName);
}
