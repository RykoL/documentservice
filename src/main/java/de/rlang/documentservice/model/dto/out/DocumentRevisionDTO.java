package de.rlang.documentservice.model.dto.out;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DocumentRevisionDTO {

    private UUID uuid;

    LocalDateTime createdAt;

}
