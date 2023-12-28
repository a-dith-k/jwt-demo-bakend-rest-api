package com.adith.demo.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    String username;
    String firstName;
    String lastName;
    String place;
    Short age;
}
