package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "slug"))
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use GenerationType.AUTO for UUID
    Integer id; // or UUID id if you prefer UUIDs

    String name;

    String slug; // You can still keep slug if needed for other purposes
}
