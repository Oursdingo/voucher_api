package com.octopus.voucher.mapper;

import com.octopus.voucher.dto.request.AccountCreateRequest;
import com.octopus.voucher.dto.request.AccountUpdateRequest;
import com.octopus.voucher.dto.response.AccountResponse;
import com.octopus.voucher.entity.Account;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface AccountMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "voucherIds", source = "vouchers")
    AccountResponse toResponse(Account entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "vouchers", ignore = true)
    Account toEntity(AccountCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "vouchers", ignore = true)
    void update(AccountUpdateRequest request, @MappingTarget Account entity);
}
