package com.netcracker.educ.printing.model.representationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MessageRepresent {
    @NonNull
    private String text;

    @NonNull
    private UUID chatId;

    private String author;

    private UUID authorId;

    private String date;
}
