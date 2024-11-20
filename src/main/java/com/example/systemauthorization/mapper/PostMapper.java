package com.example.systemauthorization.mapper;

import com.example.systemauthorization.dto.PostDto;
import com.example.systemauthorization.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toEntity(PostDto postDto);
    PostDto toDto(Post post);
}
