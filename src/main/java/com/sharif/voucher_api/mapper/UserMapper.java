package com.sharif.voucher_api.mapper;

import com.sharif.voucher_api.dto.request.UserCreateRequest;
import com.sharif.voucher_api.dto.request.UserUpdateRequest;
import com.sharif.voucher_api.dto.response.UserResponse;
import com.sharif.voucher_api.entityandrepo.user.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface UserMapper {
    @Mapping(target = "agenceId", source = "agence.id")
    UserResponse toResponse(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "agence", source = "agenceId")
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "events", ignore = true)
    UserEntity toEntity(UserCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "agence", source = "agenceId")
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "events", ignore = true)
    void update(UserUpdateRequest request, @MappingTarget UserEntity entity);
}
