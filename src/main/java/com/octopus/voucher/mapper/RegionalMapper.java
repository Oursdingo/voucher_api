package com.octopus.voucher.mapper;

import com.octopus.voucher.dto.request.RegionalCreateRequest;
import com.octopus.voucher.dto.request.RegionalUpdateRequest;
import com.octopus.voucher.dto.response.RegionalResponse;
import com.octopus.voucher.entity.Regional;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface RegionalMapper {
    @Mapping(target = "agenceIds", source = "agences")
    RegionalResponse toResponse(Regional entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "agences", ignore = true)
    Regional toEntity(RegionalCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "agences", ignore = true)
    void update(RegionalUpdateRequest request, @MappingTarget Regional entity);
}
