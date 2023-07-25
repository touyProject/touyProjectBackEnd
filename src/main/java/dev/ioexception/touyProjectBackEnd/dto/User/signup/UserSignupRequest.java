package dev.ioexception.touyProjectBackEnd.dto.User.signup;

import lombok.Data;

@Data
public class UserSignupRequest {
    String email;
    String password;
    String nickname;
}
