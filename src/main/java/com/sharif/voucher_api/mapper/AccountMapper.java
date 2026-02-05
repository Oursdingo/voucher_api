package com.sharif.voucher_api.mapper;

import com.sharif.voucher_api.dto.request.AccountCreateRequest;
import com.sharif.voucher_api.dto.request.AccountUpdateRequest;
import com.sharif.voucher_api.dto.response.AccountResponse;
import com.sharif.voucher_api.entityandrepo.account.AccountEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface AccountMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "voucherIds", source = "vouchers")
    AccountResponse toResponse(AccountEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "vouchers", ignore = true)
    AccountEntity toEntity(AccountCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "vouchers", ignore = true)
    void update(AccountUpdateRequest request, @MappingTarget AccountEntity entity);
}
