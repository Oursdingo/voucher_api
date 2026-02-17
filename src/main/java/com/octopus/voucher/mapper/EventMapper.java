package com.octopus.voucher.mapper;

import com.octopus.voucher.dto.request.EventCreateRequest;
import com.octopus.voucher.dto.request.EventUpdateRequest;
import com.octopus.voucher.dto.response.EventResponse;
import com.octopus.voucher.entity.Event;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface EventMapper {
    @Mapping(target = "userId", source = "user.id")
    EventResponse toResponse(Event entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userId")
    Event toEntity(EventCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", source = "userId")
    void update(EventUpdateRequest request, @MappingTarget Event entity);
}
