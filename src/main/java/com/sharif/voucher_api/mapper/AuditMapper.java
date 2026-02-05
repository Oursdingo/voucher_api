package com.sharif.voucher_api.mapper;

import com.sharif.voucher_api.dto.request.AuditCreateRequest;
import com.sharif.voucher_api.dto.request.AuditUpdateRequest;
import com.sharif.voucher_api.dto.response.AuditResponse;
import com.sharif.voucher_api.entityandrepo.audit.AuditEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface AuditMapper {
    @Mapping(target = "voucherId", source = "voucher.id")
    @Mapping(target = "userId", source = "user.id")
    AuditResponse toResponse(AuditEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "voucher", source = "voucherId")
    @Mapping(target = "user", source = "userId")
    AuditEntity toEntity(AuditCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(AuditUpdateRequest request, @MappingTarget AuditEntity entity);
}
