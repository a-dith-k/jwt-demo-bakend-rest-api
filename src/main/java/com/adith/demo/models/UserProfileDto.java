package com.adith.demo.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    Integer id;
    String username;
    String firstName;
    String lastName;
    String place;
    Short age;
}
