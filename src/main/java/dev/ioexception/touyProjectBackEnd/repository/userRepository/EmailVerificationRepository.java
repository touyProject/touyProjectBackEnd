package dev.ioexception.touyProjectBackEnd.repository.userRepository;

import dev.ioexception.touyProjectBackEnd.entity.UserEmailVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<UserEmailVerificationCode, Long> {
    UserEmailVerificationCode findByEmail(String email);
    UserEmailVerificationCode findByVerificationCode(String code);
}
