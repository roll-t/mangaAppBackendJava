package com.example.backend.entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


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
    Date creationTime;

    @ManyToMany
    Set<Role> roles;

    // One-to-Many relationship with BookData
    @OneToMany(mappedBy = "user")
    Set<BookData> books;

    @PrePersist
    public void prePersist() {
        if (this.uid == null || this.uid.isEmpty()) {
            this.uid = UUID.randomUUID().toString();
        }
    }

}
