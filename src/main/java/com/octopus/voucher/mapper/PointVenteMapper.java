package com.octopus.voucher.mapper;

import com.octopus.voucher.dto.request.PointVenteCreateRequest;
import com.octopus.voucher.dto.request.PointVenteUpdateRequest;
import com.octopus.voucher.dto.response.PointVenteResponse;
import com.octopus.voucher.entity.PointVente;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface PointVenteMapper {
    @Mapping(target = "agenceId", source = "agence.id")
    PointVenteResponse toResponse(PointVente entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "agence", source = "agenceId")
    PointVente toEntity(PointVenteCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "agence", source = "agenceId")
    void update(PointVenteUpdateRequest request, @MappingTarget PointVente entity);
}
