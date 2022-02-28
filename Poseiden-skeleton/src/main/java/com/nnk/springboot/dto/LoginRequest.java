package com.nnk.springboot.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "Username is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
            message = "The maximum length for username"
                    + " should be 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_USERNAME,
            message = "Should be alphanumeric or email and"
                    + " minimum more than 2 characters")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
            message = "The maximum length for password should"
                    + " be 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_PASSWORD,
            message = "The password must contain at least"
                    + " 8 characters that includes"
                    + " any one uppercase letter,"
                    + " any one number and"
                    + " any one symbol ( & ~ # @ = * - + € ^ $ £ µ % )")
    private String password;
}
