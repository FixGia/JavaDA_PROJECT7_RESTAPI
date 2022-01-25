package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleRequest {


    private Integer id;


    @NotBlank(message = "Name is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125, message = "Max length must be 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC, message = "must be alphanumeric, minimum 2 characters" )
    private String name;


    @NotBlank(message = "Description is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125, message = "Max length must be 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC, message = "must be alphanumeric, minimum 2 characters" )
    private String description;


    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125, message = "Max length must be 125 characters")
    private String json;


    @Length(max = GeneralConstraints.VARIABLE_LENGTH_512, message = "Max length must be 512 characters")
    private String template;


    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125, message = "Max length must be 125 characters")
    private String sqlStr;


    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125, message = "Max length must be 125 characters")
    private String sqlPart;


}
