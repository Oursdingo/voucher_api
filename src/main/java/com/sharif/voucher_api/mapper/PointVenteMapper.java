package com.sharif.voucher_api.mapper;

import com.sharif.voucher_api.dto.request.PointVenteCreateRequest;
import com.sharif.voucher_api.dto.request.PointVenteUpdateRequest;
import com.sharif.voucher_api.dto.response.PointVenteResponse;
import com.sharif.voucher_api.entityandrepo.pointVente.PointVenteEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface PointVenteMapper {
    @Mapping(target = "agenceId", source = "agence.id")
    PointVenteResponse toResponse(PointVenteEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "agence", source = "agenceId")
    PointVenteEntity toEntity(PointVenteCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "agence", source = "agenceId")
    void update(PointVenteUpdateRequest request, @MappingTarget PointVenteEntity entity);
}
