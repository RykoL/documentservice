package de.rlang.documentservice.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Table(name = "project")
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uuid;

    String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="owner_id")
    User creator;

    LocalDateTime createdAt;

    @ManyToMany
    Set<User>  participants;

    @PrePersist
    public void fill() {
        uuid = UUID.randomUUID();
        participants = new HashSet<User>();
    }
}
