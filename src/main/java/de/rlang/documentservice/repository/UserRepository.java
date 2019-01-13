package de.rlang.documentservice.repository;

import de.rlang.documentservice.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
     User findFirstByUserUuid(UUID uuid);
}
