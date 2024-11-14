package com.example.systemauthorization.mapper;

import com.example.systemauthorization.dto.FriendshipDto;
import com.example.systemauthorization.entity.Friendship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {

    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "receiver.id", target = "receiverId")
    FriendshipDto toDto(Friendship friendship);

    @Mapping(source = "senderId", target = "sender.id")
    @Mapping(source = "receiverId", target = "receiver.id")
    Friendship toEntity(FriendshipDto dto);

}
