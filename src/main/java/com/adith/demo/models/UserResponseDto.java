package com.adith.demo.models;

import lombok.*;
import org.modelmapper.internal.bytebuddy.asm.Advice;

@Getter
@Setter
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
