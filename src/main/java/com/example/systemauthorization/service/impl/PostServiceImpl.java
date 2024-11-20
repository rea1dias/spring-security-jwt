package com.example.systemauthorization.service.impl;

import com.example.systemauthorization.dto.PostDto;
import com.example.systemauthorization.entity.Post;
import com.example.systemauthorization.entity.User;
import com.example.systemauthorization.mapper.PostMapper;
import com.example.systemauthorization.repository.PostRepository;
import com.example.systemauthorization.repository.UserRepository;
import com.example.systemauthorization.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final PostMapper mapper;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository repository,
                           PostMapper mapper,
                           UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<PostDto> getPostsByUserId(Long userId) {
        List<Post> posts = repository.findAllByUserId(userId);
        return posts.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createPost(Long userId, PostDto postDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUser(user);
        post.setUsername(user.getUsername());
        post.setCreatedAt(LocalDateTime.now());
        repository.save(post);
    }

    @Override
    public void editPost(Long postId, PostDto dto) {
        Post post = repository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Post not found"));
        Post updatedPost = mapper.toEntity(dto);
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setCreatedAt(LocalDateTime.now());
        repository.save(post);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = repository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        repository.delete(post);
    }
}
