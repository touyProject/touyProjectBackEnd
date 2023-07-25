package dev.ioexception.touyProjectBackEnd.dto.User.login;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
}
