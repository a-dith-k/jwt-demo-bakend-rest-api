package com.adith.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data

public class LoginResponse {

    Integer statusCode;
    String message;
}
