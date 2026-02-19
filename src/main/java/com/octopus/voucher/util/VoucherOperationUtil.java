package com.octopus.voucher.util;

import com.octopus.voucher.entity.Account;
import com.octopus.voucher.enumeration.OperationEnum;
import com.octopus.voucher.error.BadRequestException;

import java.math.BigDecimal;

public final class VoucherOperationUtil {
    private VoucherOperationUtil() {
    }

    public static void applyOperation(Account account, BigDecimal amount, OperationEnum operation) {
        if (account == null) {
            throw new IllegalArgumentException("Account is required");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        if (operation == null) {
            throw new IllegalArgumentException("Operation is required");
        }

        BigDecimal currentBalance = account.getBalance() == null ? BigDecimal.ZERO : account.getBalance();
        switch (operation) {
            case RECHARGE -> account.setBalance(currentBalance.add(amount));
            case RETRAIT -> {
                if (currentBalance.compareTo(amount) < 0) {
                    throw new BadRequestException("Insufficient balance");
                }
                account.setBalance(currentBalance.subtract(amount));
            }
            default -> throw new BadRequestException("Unsupported operation");
        }
    }
}
