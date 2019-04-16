package de.rlang.documentservice.repository;

import de.rlang.documentservice.model.entity.DocumentRevision;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRevisionRepository extends CrudRepository<DocumentRevision, Long> {
}
