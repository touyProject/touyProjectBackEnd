package dev.ioexception.touyProjectBackEnd.util;

import dev.ioexception.touyProjectBackEnd.config.PasswordEncoderConfig;
import dev.ioexception.touyProjectBackEnd.entity.UserEmailVerificationCode;
import dev.ioexception.touyProjectBackEnd.repository.userRepository.EmailVerificationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmailVerification {
    private final PasswordEncoderConfig passwordEncode;
    private final JavaMailSender javaMailSender;
    private final EmailVerificationRepository signupEmailVerificationRepository;

    public void sendEmail(String email) throws MessagingException {
        String code = passwordEncode.passwordEncoder().encode(email);
        signupEmailVerificationRepository.save(new UserEmailVerificationCode(email, code));

        String urlCode = URLEncoder.encode(code, StandardCharsets.UTF_8);
        MimeMessage message = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setSubject("여깃어때 인증 링크입니다.");
        helper.setText(String.format(
                "<a href=\"http://127.0.0.1/verificationEmail?code=%s\" target=\"_blank\">여기</a>를 클릭하면 인증이 완료됩니다.",
                urlCode), true);

        this.javaMailSender.send(message);
    }
}
