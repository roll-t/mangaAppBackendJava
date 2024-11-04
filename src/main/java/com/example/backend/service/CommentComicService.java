package com.example.backend.service;

import com.example.backend.dto.request.CommentComicRequest;
import com.example.backend.dto.response.CommentComicResponse;
import com.example.backend.dto.response.CommentResponse;
import com.example.backend.entity.CommentComic;
import com.example.backend.entity.User;
import com.example.backend.mapper.CommentComicMapper;
import com.example.backend.mapper.CommentMapper;
import com.example.backend.repository.CommentComicRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentComicService {

    @Autowired
    private CommentComicRepository commentComicRepository;

    @Autowired
    private UserRepository userRepository; // Inject the User repository


    @Autowired
    private CommentComicMapper commentComicMapper;
    private CommentMapper commentMapper;

    public CommentResponse createComment(CommentComicRequest request) {
        Optional<User> userOptional = userRepository.findById(request.getUserId());

        CommentComic comment = commentComicMapper.toCommentComic(request);
        comment.setUser(userOptional.get());
        commentComicRepository.save(comment);
        return commentComicMapper.toCommentResponse(comment);
    }

    public List<CommentResponse> getCommentsByComicId(String comicId, int page) {
        // Tạo đối tượng Pageable với kích thước 20
        Pageable pageable = PageRequest.of(page, 20);

        // Sử dụng repository để lấy Page<CommentComic>
        Page<CommentComic> commentsPage = commentComicRepository.findByComicId(comicId, pageable);

        return commentsPage.stream()
                .map(commentComicMapper::toCommentResponse)
                .collect(Collectors.toList());
    }


    public Optional<CommentComicResponse> getCommentById(String commentId) {
        return commentComicRepository.findById(commentId)
                .map(commentComicMapper::toCommentComicResponse);
    }

    public void deleteComment(String commentId) {
        commentComicRepository.deleteById(commentId);
    }
}
