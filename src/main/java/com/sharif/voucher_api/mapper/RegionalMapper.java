package com.sharif.voucher_api.mapper;

import com.sharif.voucher_api.dto.request.RegionalCreateRequest;
import com.sharif.voucher_api.dto.request.RegionalUpdateRequest;
import com.sharif.voucher_api.dto.response.RegionalResponse;
import com.sharif.voucher_api.entityandrepo.regionalManagement.RegionalEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface RegionalMapper {
    @Mapping(target = "agenceIds", source = "agences")
    RegionalResponse toResponse(RegionalEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "agences", ignore = true)
    RegionalEntity toEntity(RegionalCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "agences", ignore = true)
    void update(RegionalUpdateRequest request, @MappingTarget RegionalEntity entity);
}
