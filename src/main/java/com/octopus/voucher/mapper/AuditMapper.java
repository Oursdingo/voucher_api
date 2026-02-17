package com.octopus.voucher.mapper;

import com.octopus.voucher.dto.request.AuditCreateRequest;
import com.octopus.voucher.dto.request.AuditUpdateRequest;
import com.octopus.voucher.dto.response.AuditResponse;
import com.octopus.voucher.entity.Audit;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface AuditMapper {
    @Mapping(target = "voucherId", source = "voucher.id")
    @Mapping(target = "userId", source = "user.id")
    AuditResponse toResponse(Audit entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "voucher", source = "voucherId")
    @Mapping(target = "user", source = "userId")
    Audit toEntity(AuditCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(AuditUpdateRequest request, @MappingTarget Audit entity);
}
