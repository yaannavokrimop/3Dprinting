package com.netcracker.educ.printing.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
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
    @ManyToOne
    private User executor;

    @NonNull
    @ManyToOne
    private User customer;

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", executor=" + executor +
                ", customer=" + customer +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id) &&
                Objects.equals(executor, chat.executor) &&
                Objects.equals(customer, chat.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, executor, customer);
    }
}
