package com.netcracker.educ.printing.payload;

import com.netcracker.educ.printing.model.bean.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 1, max = 45)
    private String name;

    @Size( max = 60)
    private String surname;

    @NotBlank
    private String role;

    @NotBlank
    @Size(min=1, max = 45)
    @Email
    private String email;

    @NotBlank
    private String password;

    @Size(max=45)
    private String phone;

    @Size(max=500)
    private String information;
}
