package com.example.backend.repository;

import com.example.backend.entity.BookData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookDataRepository extends JpaRepository<BookData, String> {
    @Query("SELECT b FROM BookData b JOIN b.category c WHERE c.slug = :categorySlug AND b.status = :status")
    Page<BookData> findByCategorySlugAndStatus(@Param("categorySlug") String categorySlug,
                                               @Param("status") String status,
                                               Pageable pageable);
}
