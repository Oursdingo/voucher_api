package com.sharif.voucher_api.mapper;

import com.sharif.voucher_api.dto.request.VoucherCreateRequest;
import com.sharif.voucher_api.dto.request.VoucherUpdateRequest;
import com.sharif.voucher_api.dto.response.VoucherResponse;
import com.sharif.voucher_api.entityandrepo.voucher.VoucherEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface VoucherMapper {
    @Mapping(target = "accountId", source = "account.id")
    VoucherResponse toResponse(VoucherEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", source = "accountId")
    VoucherEntity toEntity(VoucherCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(VoucherUpdateRequest request, @MappingTarget VoucherEntity entity);
}
