package dev.ioexception.touyProjectBackEnd.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegexValidation {
    String emailRegex = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.+ac\\.kr$";
    String nicknameRegex = "^[a-zA-Z0-9가-힣]{3,10}$";
    String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";


    public boolean isEmailRegex(String email) {

        return email.matches(emailRegex);
    }

    public boolean isNicknameRegex(String nickname)
    {
        return nickname.matches(nicknameRegex);
    }

    public boolean isPasswordRegex(String password) {
        return password.matches(passwordRegex);
    }
}
