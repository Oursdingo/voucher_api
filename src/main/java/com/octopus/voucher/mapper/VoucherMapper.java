package com.octopus.voucher.mapper;

import com.octopus.voucher.dto.request.VoucherCreateRequest;
import com.octopus.voucher.dto.request.VoucherUpdateRequest;
import com.octopus.voucher.dto.response.VoucherResponse;
import com.octopus.voucher.entity.Voucher;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface VoucherMapper {
    @Mapping(target = "accountId", source = "account.id")
    VoucherResponse toResponse(Voucher entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", source = "accountId")
    Voucher toEntity(VoucherCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(VoucherUpdateRequest request, @MappingTarget Voucher entity);
}
