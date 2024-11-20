package com.example.systemauthorization.service;

import com.example.systemauthorization.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    List<PostDto> getPostsByUserId(Long userId);
    void createPost(Long userId, PostDto postDto);
    void editPost(Long postId, PostDto dto);
    void deletePost(Long postId);
}
