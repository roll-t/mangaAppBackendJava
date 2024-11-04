package com.example.backend.controller;

import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.CommentComicRequest;
import com.example.backend.dto.response.CommentComicResponse;
import com.example.backend.dto.response.CommentResponse;
import com.example.backend.service.CommentComicService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comic/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "http://localhost:3000")
public class CommentComicController {

    CommentComicService commentComicService;

    @PostMapping("/create")
    public ApiResponse<CommentResponse> createComment(
            @RequestBody @Valid CommentComicRequest commentComicRequest) {
        CommentResponse commentComicResponse = commentComicService.createComment(commentComicRequest);
        return ApiResponse.<CommentResponse>builder().result(commentComicResponse).build();
    }

    @GetMapping("/all/{comicId}")
    public ApiResponse<List<CommentResponse>> getCommentsByComicId(
            @PathVariable String comicId,
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<CommentResponse> comments = commentComicService.getCommentsByComicId(comicId, page);
            return ApiResponse.<List<CommentResponse>>builder().result(comments).build();
        } catch (RuntimeException e) {
            return ApiResponse.<List<CommentResponse>>builder().message(e.getMessage()).build();
        }
    }

    @GetMapping("/{commentId}")
    public ApiResponse<CommentComicResponse> getCommentById(
            @PathVariable String commentId) {
        try {
            CommentComicResponse comment = commentComicService.getCommentById(commentId)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));
            return ApiResponse.<CommentComicResponse>builder().result(comment).build();
        } catch (RuntimeException e) {
            return ApiResponse.<CommentComicResponse>builder().message(e.getMessage()).build();
        }
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Boolean> deleteComment(@PathVariable String commentId) {
        try {
            commentComicService.deleteComment(commentId);
            return ApiResponse.<Boolean>builder().result(true).build();
        } catch (RuntimeException e) {
            return ApiResponse.<Boolean>builder().result(false).build();
        }
    }

}
