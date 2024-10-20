package com.example.backend.controller;


import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.ChapterRequest;
import com.example.backend.dto.response.ChapterResponse;
import com.example.backend.service.ChapterService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapter")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
@CrossOrigin(origins = "http://localhost:3000")
public class ChapterController {
    ChapterService chapterService;

    @PostMapping("/{bid}")
    public ApiResponse<ChapterResponse> createChapter(@RequestBody @Valid ChapterRequest chapterRequest, @PathVariable("bid") String bid) {
        return ApiResponse.<ChapterResponse>builder()
                .result(chapterService.create(chapterRequest, bid))
                .build();
    }

    @GetMapping("/book/{slug}")
    public ApiResponse<List<ChapterResponse>> getChaptersByBookDataSlug(@PathVariable String slug) {
        return ApiResponse.<List<ChapterResponse>>builder().result(chapterService.getChaptersByBookDataSlug(slug)).build();
    }

    @DeleteMapping("/{chapterId}")
    public ApiResponse<Boolean> deleteChapter(@PathVariable("chapterId") String chapterId) {
        try {
            chapterService.delete(chapterId);
            return ApiResponse.<Boolean>builder().result(true).build();
        } catch (Exception e) {
            return ApiResponse.<Boolean>builder().result(false).build();
        }
    }

    @PutMapping("/{chapterId}")
    public ApiResponse<ChapterResponse> updateChapter(@RequestBody @Valid ChapterRequest chapterRequest, @PathVariable("chapterId") String chapterId) {
        return ApiResponse.<ChapterResponse>builder().result(chapterService.update(chapterId, chapterRequest)).build();
    }

    @GetMapping("/{chapterId}")
    public ApiResponse<ChapterResponse> getChapter(@PathVariable("chapterId") String chapterId) {
        return ApiResponse.<ChapterResponse>builder().result(chapterService.getChapterById(chapterId)).build();
    }
}
