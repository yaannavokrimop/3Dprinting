package com.netcracker.educ.printing.model.representationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class MessageRepresent {
    @NonNull
    @JsonProperty
    private String text;

    @NonNull
    @JsonProperty
    private UUID chatId;

    private String author;

    private UUID authorId;

    private String date;
}
