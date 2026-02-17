package com.octopus.voucher.mapper;

import com.octopus.voucher.dto.request.UserCreateRequest;
import com.octopus.voucher.dto.request.UserUpdateRequest;
import com.octopus.voucher.dto.response.UserResponse;
import com.octopus.voucher.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface UserMapper {
    @Mapping(target = "agenceId", source = "agence.id")
    UserResponse toResponse(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "agence", source = "agenceId")
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "events", ignore = true)
    User toEntity(UserCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "agence", source = "agenceId")
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "events", ignore = true)
    void update(UserUpdateRequest request, @MappingTarget User entity);
}
