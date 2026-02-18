package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.AuditCreateRequest;
import com.octopus.voucher.dto.request.AuditUpdateRequest;
import com.octopus.voucher.dto.response.AuditResponse;
import com.octopus.voucher.entity.Audit;
import com.octopus.voucher.enumeration.EtatEnum;
import com.octopus.voucher.enumeration.OperationEnum;
import com.octopus.voucher.enumeration.PlateformEnum;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.AuditMapper;
import com.octopus.voucher.repository.AuditRepository;
import com.octopus.voucher.repository.UserRepository;
import com.octopus.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {
    @Mock
    private AuditRepository auditRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private VoucherRepository voucherRepository;
    @Mock
    private AuditMapper auditMapper;
    @InjectMocks
    private AuditService auditService;

    @Test
    void shouldThrowOnCreateWhenUserMissing() {
        UUID userId = UUID.randomUUID();
        AuditCreateRequest request = AuditCreateRequest.builder()
                .code("AUD001")
                .operationEnum(OperationEnum.RECHARGE)
                .etatEnum(EtatEnum.VALID)
                .plateformEnum(PlateformEnum.ECD)
                .numeroTelephone("12345678")
                .amount(BigDecimal.valueOf(100))
                .eventDate(LocalDateTime.now())
                .userId(userId)
                .build();

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThatThrownBy(() -> auditService.create(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void shouldThrowOnCreateWhenVoucherMissing() {
        UUID voucherId = UUID.randomUUID();
        AuditCreateRequest request = AuditCreateRequest.builder()
                .code("AUD001")
                .operationEnum(OperationEnum.RECHARGE)
                .etatEnum(EtatEnum.VALID)
                .plateformEnum(PlateformEnum.ECD)
                .numeroTelephone("12345678")
                .amount(BigDecimal.valueOf(100))
                .eventDate(LocalDateTime.now())
                .voucherId(voucherId)
                .build();

        when(voucherRepository.existsById(voucherId)).thenReturn(false);

        assertThatThrownBy(() -> auditService.create(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Voucher not found");
    }

    @Test
    void shouldCreateAudit() {
        UUID userId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        AuditCreateRequest request = AuditCreateRequest.builder()
                .code("AUD001")
                .operationEnum(OperationEnum.RECHARGE)
                .etatEnum(EtatEnum.VALID)
                .plateformEnum(PlateformEnum.ECD)
                .numeroTelephone("12345678")
                .amount(BigDecimal.valueOf(100))
                .eventDate(LocalDateTime.now())
                .userId(userId)
                .voucherId(voucherId)
                .build();

        Audit entity = Audit.builder().id(UUID.randomUUID()).build();
        AuditResponse response = AuditResponse.builder().id(entity.getId()).build();

        when(userRepository.existsById(userId)).thenReturn(true);
        when(voucherRepository.existsById(voucherId)).thenReturn(true);
        when(auditMapper.toEntity(request)).thenReturn(entity);
        when(auditRepository.save(entity)).thenReturn(entity);
        when(auditMapper.toResponse(entity)).thenReturn(response);

        AuditResponse result = auditService.create(request);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void shouldThrowOnUpdateWhenAuditMissing() {
        UUID id = UUID.randomUUID();
        AuditUpdateRequest request = AuditUpdateRequest.builder().id(id).build();

        when(auditRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> auditService.update(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Audit not found");
    }

    @Test
    void shouldUpdateAudit() {
        UUID id = UUID.randomUUID();
        Audit entity = Audit.builder().id(id).build();
        AuditUpdateRequest request = AuditUpdateRequest.builder()
                .id(id)
                .etatEnum(EtatEnum.NON_VALIDE)
                .build();
        AuditResponse response = AuditResponse.builder().id(id).build();

        when(auditRepository.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(auditMapper).update(request, entity);
        when(auditRepository.save(entity)).thenReturn(entity);
        when(auditMapper.toResponse(entity)).thenReturn(response);

        AuditResponse result = auditService.update(request);

        assertThat(result).isEqualTo(response);
        verify(auditMapper).update(request, entity);
    }
}
