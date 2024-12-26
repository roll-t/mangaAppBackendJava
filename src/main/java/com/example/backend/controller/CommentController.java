package com.example.backend.controller;

import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.CommentRequest;
import com.example.backend.dto.response.CommentResponse;
import com.example.backend.service.CommentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    CommentService commentService;

    @PostMapping("/{bookDataId}/create")
    public ApiResponse<CommentResponse> createComment(
            @PathVariable String bookDataId,
            @RequestBody @Valid CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.createComment(bookDataId, commentRequest);
        return ApiResponse.<CommentResponse>builder().result(commentResponse).build();
    }

    @GetMapping("/{bookDataId}")
    public ApiResponse<List<CommentResponse>> getCommentsByBookId(
            @PathVariable String bookDataId,
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<CommentResponse> comments = commentService.getCommentsByBookId(bookDataId, page);
            return ApiResponse.<List<CommentResponse>>builder().result(comments).build();
        } catch (RuntimeException e) {
            return ApiResponse.<List<CommentResponse>>builder().message(e.getMessage()).build();
        }
    }

    @PutMapping("/{commentId}")
    public ApiResponse<Boolean> updateComment(
            @PathVariable String commentId,
            @RequestBody @Valid CommentRequest commentRequest) {
        boolean isUpdated = commentService.updateComment(commentId, commentRequest);
        return ApiResponse.<Boolean>builder().result(isUpdated).build();
    }


    @GetMapping("/chapter/{chapterId}")
    public ApiResponse<List<CommentResponse>> getCommentsByChapterId(
            @PathVariable String chapterId,
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<CommentResponse> comments = commentService.getCommentsByChapterId(chapterId, page);
            return ApiResponse.<List<CommentResponse>>builder().result(comments).build();
        } catch (RuntimeException e) {
            return ApiResponse.<List<CommentResponse>>builder().message(e.getMessage()).build();
        }
    }


    @DeleteMapping("/{commentId}")
    public ApiResponse<Boolean> deleteComment(@PathVariable String commentId) {
        boolean isDeleted = commentService.deleteComment(commentId);
        return ApiResponse.<Boolean>builder().result(isDeleted).build();
    }
}
