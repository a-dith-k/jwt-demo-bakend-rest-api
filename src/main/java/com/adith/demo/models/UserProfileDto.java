package com.adith.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    String username;
    String firstName;
    String lastName;
    String place;
    Short age;
}
