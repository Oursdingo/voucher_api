package com.sharif.voucher_api.mapper;

import com.sharif.voucher_api.entityandrepo.account.AccountEntity;
import com.sharif.voucher_api.entityandrepo.agence.AgenceEntity;
import com.sharif.voucher_api.entityandrepo.pointVente.PointVenteEntity;
import com.sharif.voucher_api.entityandrepo.regionalManagement.RegionalEntity;
import com.sharif.voucher_api.entityandrepo.user.UserEntity;
import com.sharif.voucher_api.entityandrepo.voucher.VoucherEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ReferenceMapper {
    default UserEntity userFromId(UUID id) {
        if (id == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(id);
        return entity;
    }

    default AgenceEntity agenceFromId(UUID id) {
        if (id == null) {
            return null;
        }
        AgenceEntity entity = new AgenceEntity();
        entity.setId(id);
        return entity;
    }

    default RegionalEntity regionalFromId(UUID id) {
        if (id == null) {
            return null;
        }
        RegionalEntity entity = new RegionalEntity();
        entity.setId(id);
        return entity;
    }

    default AccountEntity accountFromId(UUID id) {
        if (id == null) {
            return null;
        }
        AccountEntity entity = new AccountEntity();
        entity.setId(id);
        return entity;
    }

    default VoucherEntity voucherFromId(UUID id) {
        if (id == null) {
            return null;
        }
        VoucherEntity entity = new VoucherEntity();
        entity.setId(id);
        return entity;
    }

    default PointVenteEntity pointVenteFromId(UUID id) {
        if (id == null) {
            return null;
        }
        PointVenteEntity entity = new PointVenteEntity();
        entity.setId(id);
        return entity;
    }

    default List<UUID> voucherIdsFromEntities(List<VoucherEntity> vouchers) {
        if (vouchers == null) {
            return List.of();
        }
        return vouchers.stream().map(VoucherEntity::getId).toList();
    }

    default List<UUID> accountIdsFromEntities(List<AccountEntity> accounts) {
        if (accounts == null) {
            return List.of();
        }
        return accounts.stream().map(AccountEntity::getId).toList();
    }

    default List<UUID> agenceIdsFromEntities(List<AgenceEntity> agences) {
        if (agences == null) {
            return List.of();
        }
        return agences.stream().map(AgenceEntity::getId).toList();
    }

    default List<UUID> pointVenteIdsFromEntities(List<PointVenteEntity> pointVentes) {
        if (pointVentes == null) {
            return List.of();
        }
        return pointVentes.stream().map(PointVenteEntity::getId).toList();
    }

    default List<UUID> userIdsFromEntities(List<UserEntity> users) {
        if (users == null) {
            return List.of();
        }
        return users.stream().map(UserEntity::getId).toList();
    }

    default List<PointVenteEntity> pointVentesFromIds(List<UUID> ids) {
        if (ids == null) {
            return List.of();
        }
        return ids.stream().map(this::pointVenteFromId).toList();
    }
}
