package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Account DTO class
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class AccountDto {
    private Integer id;
    private String account;
}