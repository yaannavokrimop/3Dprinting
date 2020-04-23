package com.netcracker.educ.printing.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "chat")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Chat {

    @Id
    @GeneratedValue
    private UUID id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User executor;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User customer;
}
