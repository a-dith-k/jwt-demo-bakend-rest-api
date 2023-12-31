package com.adith.demo.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

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
    Boolean isEnabled;
}
