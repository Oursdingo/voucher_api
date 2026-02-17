package com.octopus.voucher.mapper;

import com.octopus.voucher.entity.Account;
import com.octopus.voucher.entity.Agence;
import com.octopus.voucher.entity.PointVente;
import com.octopus.voucher.entity.Regional;
import com.octopus.voucher.entity.User;
import com.octopus.voucher.entity.Voucher;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ReferenceMapper {
    default User userFromId(UUID id) {
        if (id == null) {
            return null;
        }
        User entity = new User();
        entity.setId(id);
        return entity;
    }

    default Agence agenceFromId(UUID id) {
        if (id == null) {
            return null;
        }
        Agence entity = new Agence();
        entity.setId(id);
        return entity;
    }

    default Regional regionalFromId(UUID id) {
        if (id == null) {
            return null;
        }
        Regional entity = new Regional();
        entity.setId(id);
        return entity;
    }

    default Account accountFromId(UUID id) {
        if (id == null) {
            return null;
        }
        Account entity = new Account();
        entity.setId(id);
        return entity;
    }

    default Voucher voucherFromId(UUID id) {
        if (id == null) {
            return null;
        }
        Voucher entity = new Voucher();
        entity.setId(id);
        return entity;
    }

    default PointVente pointVenteFromId(UUID id) {
        if (id == null) {
            return null;
        }
        PointVente entity = new PointVente();
        entity.setId(id);
        return entity;
    }

    default List<UUID> voucherIdsFromEntities(List<Voucher> vouchers) {
        if (vouchers == null) {
            return List.of();
        }
        return vouchers.stream().map(Voucher::getId).toList();
    }

    default List<UUID> accountIdsFromEntities(List<Account> accounts) {
        if (accounts == null) {
            return List.of();
        }
        return accounts.stream().map(Account::getId).toList();
    }

    default List<UUID> agenceIdsFromEntities(List<Agence> agences) {
        if (agences == null) {
            return List.of();
        }
        return agences.stream().map(Agence::getId).toList();
    }

    default List<UUID> pointVenteIdsFromEntities(List<PointVente> pointVentes) {
        if (pointVentes == null) {
            return List.of();
        }
        return pointVentes.stream().map(PointVente::getId).toList();
    }

    default List<UUID> userIdsFromEntities(List<User> users) {
        if (users == null) {
            return List.of();
        }
        return users.stream().map(User::getId).toList();
    }

    default List<PointVente> pointVentesFromIds(List<UUID> ids) {
        if (ids == null) {
            return List.of();
        }
        return ids.stream().map(this::pointVenteFromId).toList();
    }
}
