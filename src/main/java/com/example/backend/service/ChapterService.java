package com.example.backend.service;

import com.example.backend.dto.request.ChapterRequest;
import com.example.backend.dto.request.ChapterUpdateRequest;
import com.example.backend.dto.response.ChapterResponse;
import com.example.backend.entity.BookData;
import com.example.backend.entity.Chapter;
import com.example.backend.mapper.ChapterMapper;
import com.example.backend.repository.BookDataRepository;
import com.example.backend.repository.ChapterRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChapterService {
    ChapterRepository chapterRepository;
    ChapterMapper chapterMapper;
    BookDataRepository bookDataRepository;

    public ChapterResponse create(ChapterRequest chapterRequest, String bookDataId) {
        BookData bookData = bookDataRepository.findById(bookDataId)
                .orElseThrow(() -> new RuntimeException("BookData not found"));

        Chapter chapter = chapterMapper.toChapter(chapterRequest);

        if (bookData != null) {
            chapter.setBookData(bookData);
        }

        chapter.setCreateAt(LocalDateTime.now());
        chapterRepository.save(chapter);
        return chapterMapper.toChapterResponse(chapter);
    }

    public List<ChapterResponse> getChaptersByBookDataSlug(String slug) {
        List<Chapter> chapters = chapterRepository.findByBookDataSlug(slug);
        return chapters.stream()
                .sorted(Comparator.comparing(Chapter::getCreateAt)) // Sort by createAt field
                .map(chapterMapper::toChapterResponse)
                .collect(Collectors.toList());
    }

    public void delete(String ChapterId) {
        chapterRepository.deleteById(ChapterId);
    }

    public ChapterResponse update(String ChapterId, ChapterRequest chapterRequest) {
        Chapter chapter = chapterRepository.findById(ChapterId).orElseThrow(() -> new RuntimeException("Category not found")); //;
        ChapterUpdateRequest chapterUpdateRequest= chapterMapper.toChapterUpdateRequest(chapterRequest);
        chapterMapper.chapterUpdate(chapter, chapterUpdateRequest);
        chapterRepository.save(chapter);
        return chapterMapper.toChapterResponse(chapter);
    }

    public ChapterResponse getChapterById(String ChapterId) {
        Chapter chapter = chapterRepository.findById(ChapterId).orElseThrow(() -> new RuntimeException("Category not found"));
        return chapterMapper.toChapterResponse(chapter);
    }
}
