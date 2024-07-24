package in.transportstack.delhi.core.repository;

import in.transportstack.delhi.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
