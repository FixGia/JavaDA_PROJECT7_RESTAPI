package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BidListRequest {

    private Integer BidListId;

    @NotBlank(message = "Account is mandatory")
    @Length(max=GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC, message = "must be alphanumeric, minimum 2 characters")
    private String account;

    @Length(max=GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC, message = "must be alphanumeric, minimum 2 characters")
    @NotBlank(message = "Type is mandatory")
    private String type;

    @Digits(integer = 12, fraction = 2, message = "max digits 12 with 2 decimals")
    @Min(value = 0, message = "must be positive number")
    private Double bidQuantity;

}
