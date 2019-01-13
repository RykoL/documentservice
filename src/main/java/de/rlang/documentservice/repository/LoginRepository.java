package de.rlang.documentservice.repository;

import de.rlang.documentservice.model.entity.Login;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends CrudRepository<Login, Long> {

    Login findByEmail(String email);
}
