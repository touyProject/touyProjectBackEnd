package dev.ioexception.touyProjectBackEnd.dto.User;

import dev.ioexception.touyProjectBackEnd.entity.User;

public record UserAccountDto(
        String email,
        String password,
        String nickname,
        String authority
) {

    public static UserAccountDto of(String email, String password, String nickname) {
        return new UserAccountDto(password, email, nickname, null);
    }

    public static UserAccountDto from(User entity) {
        return new UserAccountDto(
                entity.getEmail(),
                entity.getPassword(),
                entity.getAuthority(),
                entity.getNickname()
        );
    }

    public User toEntity() {
        return User.of(
                email,
                password,
                nickname,
                authority
        );
    }

}
