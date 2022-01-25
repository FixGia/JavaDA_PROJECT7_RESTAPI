package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserRequest {

    private Integer id;


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
    @Pattern(regexp = UserConstraints.PATTERN_PASSWORD,
            message = "The password must contain at least"
                    + " 8 characters that includes"
                    + " any one uppercase letter,"
                    + " any one number and"
                    + " any one symbol ( & ~ # @ = * - + € ^ $ £ µ % )")
    private String password;


    @NotBlank(message = "FullName is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
            message = "The maximum length for FullName should"
                    + " be 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
            message = "Should be alphanumeric and minimum more"
                    + " than 2 characters")
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHABET_CHARACTERS,
            message = "Should be alphabets and minimum more than"
                    + " 2 characters")
    private String role;

    // ############################################################


}
