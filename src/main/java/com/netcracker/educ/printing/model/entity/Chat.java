package com.netcracker.educ.printing.model.entity;

import com.netcracker.educ.printing.model.bean.ChatId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "chat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @EmbeddedId
    private ChatId id;

    @MapsId("executorId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User executor;

    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User customer;
}
