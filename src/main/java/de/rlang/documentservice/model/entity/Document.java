package de.rlang.documentservice.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table(name = "document")
@Entity
public class Document {
}
