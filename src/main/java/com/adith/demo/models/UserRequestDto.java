package com.adith.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestDto {

    @NotNull
    @NotBlank
    @Size(max = 10,min=6)
    String username;
    @NotNull
    @NotBlank
    @Size(max = 16,min = 8)
    String password;
    String firstName;
    String lastName;
    String place;
    Short age;
    Boolean isEnabled=true;
}
