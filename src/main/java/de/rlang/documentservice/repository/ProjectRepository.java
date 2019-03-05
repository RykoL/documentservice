package de.rlang.documentservice.repository;

import de.rlang.documentservice.model.entity.Project;
import de.rlang.documentservice.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByUuid(UUID uuid);

    List<Project> findAllByUser(User user);
}
