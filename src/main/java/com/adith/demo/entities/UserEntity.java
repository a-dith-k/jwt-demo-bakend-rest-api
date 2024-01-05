package com.adith.demo.entities;


import com.adith.demo.enums.UserRole;
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
    Boolean isEnabled;
    UserRole userRole;
}
