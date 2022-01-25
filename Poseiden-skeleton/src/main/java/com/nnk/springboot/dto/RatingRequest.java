package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingRequest {

    private Integer id;


    @NotBlank(message = "moodysRating is mandatory")
    @Length(max=GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC, message = "must be alphanumeric, minimum 2 characters")
    private String moodysRating;

    @NotBlank(message = "sandPRating is mandatory")
    @Length(max=GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC, message = "must be alphanumeric, minimum 2 characters")
    private String sandPRating;

    @NotBlank(message = "fitchRating is mandatory")
    @Length(max=GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC, message = "must be alphanumeric, minimum 2 characters")
    private String fitchRating;


    @Min(value = 0, message ="The value must be positive")
    private Integer orderNumber;


}
