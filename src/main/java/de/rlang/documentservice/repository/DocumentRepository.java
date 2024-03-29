package de.rlang.documentservice.repository;

import de.rlang.documentservice.model.entity.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long>{
}
