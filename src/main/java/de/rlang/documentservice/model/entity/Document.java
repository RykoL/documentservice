package de.rlang.documentservice.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Table(name = "document")
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uuid;

    private String name;

    @OneToMany
    @JoinColumn(name = "document_id")
    private Set<DocumentRevision> documentRevisions;

    @PrePersist
    public void fill() {
        uuid = UUID.randomUUID();
    }
}
