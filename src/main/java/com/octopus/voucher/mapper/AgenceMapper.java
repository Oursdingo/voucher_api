package com.octopus.voucher.mapper;

import com.octopus.voucher.dto.request.AgenceCreateRequest;
import com.octopus.voucher.dto.request.AgenceUpdateRequest;
import com.octopus.voucher.dto.response.AgenceResponse;
import com.octopus.voucher.entity.Agence;
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
    AgenceResponse toResponse(Agence entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "regional", source = "regionalId")
    @Mapping(target = "pointVentes", source = "pointVenteIds")
    @Mapping(target = "users", ignore = true)
    Agence toEntity(AgenceCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "regional", source = "regionalId")
    @Mapping(target = "pointVentes", source = "pointVenteIds")
    @Mapping(target = "users", ignore = true)
    void update(AgenceUpdateRequest request, @MappingTarget Agence entity);
}
