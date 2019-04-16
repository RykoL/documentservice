package de.rlang.documentservice.model.dto.out;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DocumentDTO {

    private UUID uuid;

    private String name;

    private List<DocumentRevisionDTO> documentRevisions;
}
