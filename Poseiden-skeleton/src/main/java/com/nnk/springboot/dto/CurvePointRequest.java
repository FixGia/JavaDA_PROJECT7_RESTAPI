package com.nnk.springboot.dto;



import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurvePointRequest {

    private Integer id;


    @NotNull(message = "CurveId is mandatory")
    @Min(value = 0, message ="The value must be positive")
    private Integer curveId;

    /** The term. */
    @NotNull(message = "Term is mandatory")
    @Digits(integer = 12, fraction = 2, message = "max digits 12 with 2 decimals")
    @Min(value = 0, message = "must be positive number")
    private Double term;

    /** The value. */
    @NotNull(message = "Value is mandatory")
    @Digits(integer = 12, fraction = 2, message = "max digits 12 with 2 decimals")
    @Min(value = 0, message = "must be positive number")
    private Double value;
}
