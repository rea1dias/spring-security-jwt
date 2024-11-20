package com.example.systemauthorization.mapper;

import com.example.systemauthorization.dto.ProfileDto;
import com.example.systemauthorization.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toEntity(ProfileDto dto);
    ProfileDto toDto(Profile profile);
    Profile toUpdatedEntity(ProfileDto dto);
}
