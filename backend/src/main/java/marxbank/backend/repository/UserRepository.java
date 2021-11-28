package marxbank.backend.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import marxbank.core.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Optional<User> findByToken_Token(String token);

}