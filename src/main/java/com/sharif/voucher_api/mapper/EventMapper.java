package com.sharif.voucher_api.mapper;

import com.sharif.voucher_api.dto.request.EventCreateRequest;
import com.sharif.voucher_api.dto.request.EventUpdateRequest;
import com.sharif.voucher_api.dto.response.EventResponse;
import com.sharif.voucher_api.entityandrepo.event.EventEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface EventMapper {
    @Mapping(target = "userId", source = "user.id")
    EventResponse toResponse(EventEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userId")
    EventEntity toEntity(EventCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", source = "userId")
    void update(EventUpdateRequest request, @MappingTarget EventEntity entity);
}
