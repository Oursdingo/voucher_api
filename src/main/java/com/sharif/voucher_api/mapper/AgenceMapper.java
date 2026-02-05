package com.sharif.voucher_api.mapper;

import com.sharif.voucher_api.dto.request.AgenceCreateRequest;
import com.sharif.voucher_api.dto.request.AgenceUpdateRequest;
import com.sharif.voucher_api.dto.response.AgenceResponse;
import com.sharif.voucher_api.entityandrepo.agence.AgenceEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface AgenceMapper {
    @Mapping(target = "regionalId", source = "regional.id")
    @Mapping(target = "pointVenteIds", source = "pointVentes")
    @Mapping(target = "userIds", source = "users")
    AgenceResponse toResponse(AgenceEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "regional", source = "regionalId")
    @Mapping(target = "pointVentes", source = "pointVenteIds")
    @Mapping(target = "users", ignore = true)
    AgenceEntity toEntity(AgenceCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "regional", source = "regionalId")
    @Mapping(target = "pointVentes", source = "pointVenteIds")
    @Mapping(target = "users", ignore = true)
    void update(AgenceUpdateRequest request, @MappingTarget AgenceEntity entity);
}
