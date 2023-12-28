package com.adith.demo.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name="users",
        uniqueConstraints =
            @UniqueConstraint(columnNames = {"username"})
)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId;
    String username;
    String firstName="Guest";
    String lastName="User";
    String place;
    String password;
    Short age;
}
