package com.octopus.voucher.dto.request;

import com.octopus.voucher.enumeration.OperationEnum;
import com.octopus.voucher.enumeration.PlateformEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherCreateRequest {
    @NotBlank
    @Pattern(regexp = "^[0-9]{8}$")
    private String numeroTelephone;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private PlateformEnum plateformEnum;

    @NotNull
    private OperationEnum operationEnum;

    private String transactionId;

    private UUID accountId;

    @NotNull
    private LocalDateTime expirationDate;

    @AssertTrue
    @JsonIgnore
    public boolean isAmountMultipleOfFifty() {
        if (amount == null) {
            return false;
        }
        return amount.remainder(BigDecimal.valueOf(50)).compareTo(BigDecimal.ZERO) == 0;
    }

    @AssertTrue
    @JsonIgnore
    public boolean isExpirationAfterNow() {
        if (expirationDate == null) {
            return false;
        }
        return expirationDate.isAfter(LocalDateTime.now());
    }
}
