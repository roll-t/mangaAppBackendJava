    package com.example.backend.entity;

    import jakarta.persistence.*;
    import lombok.*;
    import lombok.experimental.FieldDefaults;

    import java.util.List;
    import java.util.UUID;

    @Entity
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Data

    public class BookModel {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String bookModelId;
        String titlePage;
        String domainImage;
    }
