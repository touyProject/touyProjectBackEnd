package dev.ioexception.touyProjectBackEnd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

/**
 * 유저 회원가입용 Dto
 */
@Getter
@Setter
@Entity
public class UserEmailVerificationCode {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String verificationCode;

    @Column
    private boolean isVerification;

    @Column
    private boolean expiredFlag;

    @CreatedDate
    @Column(insertable = false, updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @CreatedDate
    @Column(insertable = false, updatable = false)
    private LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(30);

    public UserEmailVerificationCode(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
    }

    public UserEmailVerificationCode() {
    }
}
