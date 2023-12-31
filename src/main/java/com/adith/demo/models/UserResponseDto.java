package com.adith.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.internal.bytebuddy.asm.Advice;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    Integer id;
    String username;
    String firstName;
    String lastName;
    String place;
    Short age;
    Boolean isEnabled;
}
