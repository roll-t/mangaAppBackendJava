package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    String uid;
    String displayName;
    String email;
    String password;
    String photoURL;
    LocalDateTime creationTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Ngăn chặn vòng lặp vô hạn
    List<Comment> comments;

    @ManyToMany
    Set<Role> roles;

    @OneToMany(mappedBy = "user")
    Set<BookData> books;

    @PrePersist
    public void prePersist() {
        if (this.uid == null || this.uid.isEmpty()) {
            this.uid = UUID.randomUUID().toString();
        }
        if (this.creationTime == null) {
            this.creationTime = LocalDateTime.now();
        }
    }
}
