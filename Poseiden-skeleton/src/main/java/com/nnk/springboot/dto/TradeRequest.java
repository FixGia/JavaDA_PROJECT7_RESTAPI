package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeRequest {

    private Integer id;

    @NotBlank(message = "account is mandatory")
    @Length(max=GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC, message = "must be alphanumeric, minimum 2 characters")
    private String account;

    @NotBlank(message = "type is mandatory")
    @Length(max=GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC, message = "must be alphanumeric, minimum 2 characters")
    private String type;

    @NotNull(message = "buyQuantity is mandatory")
    @Digits(integer = 12, fraction = 2, message = "max digits 12 with 2 decimals")
    @Min(value = 0, message = "must be positive number")
    private Double buyQuantity;

}
