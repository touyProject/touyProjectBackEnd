package dev.ioexception.touyProjectBackEnd.service.userService;

import dev.ioexception.touyProjectBackEnd.config.PasswordEncoderConfig;
import dev.ioexception.touyProjectBackEnd.dto.User.findPW.FindPassword;
import dev.ioexception.touyProjectBackEnd.dto.User.findPW.FindPasswordByEmailRequest;
import dev.ioexception.touyProjectBackEnd.dto.User.modifyUserInfo.ModifyNickname;
import dev.ioexception.touyProjectBackEnd.dto.User.modifyUserInfo.ModifyPassword;
import dev.ioexception.touyProjectBackEnd.dto.User.security.jwt.JwtTokenProvider;
import dev.ioexception.touyProjectBackEnd.dto.User.security.jwt.TokenInfo;
import dev.ioexception.touyProjectBackEnd.dto.User.signup.UserSignupRequest;
import dev.ioexception.touyProjectBackEnd.entity.User;
import dev.ioexception.touyProjectBackEnd.entity.UserEmailVerificationCode;
import dev.ioexception.touyProjectBackEnd.repository.userRepository.EmailVerificationRepository;
import dev.ioexception.touyProjectBackEnd.repository.userRepository.UserRepository;
import dev.ioexception.touyProjectBackEnd.util.DuplicationValidation;
import dev.ioexception.touyProjectBackEnd.util.EmailVerification;
import dev.ioexception.touyProjectBackEnd.util.RegexValidation;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final PasswordEncoderConfig passwordEncode;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final DuplicationValidation duplicationValidation;
    private final EmailVerification emailVerification;

    RegexValidation regexValidation = new RegexValidation();

    public Optional<User> findUser(String email) {
        return userRepository.findByEmail(email);

    }

    @Transactional
    public TokenInfo login(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
                password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }


    public boolean signup(UserSignupRequest userSignupRequest) throws Exception {
        if (!duplicationValidation.isEmailDuplication(userSignupRequest.getEmail())
                || !regexValidation.isEmailRegex(userSignupRequest.getEmail())) {
            log.warn("email fail");
            return false;
        }
        if (!regexValidation.isPasswordRegex(userSignupRequest.getPassword())) {
            log.warn("password fail");
            return false;
        }
        if (!duplicationValidation.isNicknameDuplication(userSignupRequest.getNickname())
                || !regexValidation.isNicknameRegex(userSignupRequest.getNickname())) {
            log.warn("nickname fail");
            return false;
        }

        sendEmail(userSignupRequest.getEmail());

        userRepository.save(new User(userSignupRequest.getEmail(),
                passwordEncode.passwordEncoder().encode(userSignupRequest.getPassword()),
                userSignupRequest.getNickname(), "USER"));

        return true;
    }

    @Transactional
    public boolean validationEmail(String code) {
        try {
            UserEmailVerificationCode verificationByCode = emailVerificationRepository.findByVerificationCode(code);
            String email = verificationByCode.getEmail();

            verificationByCode.setVerification(true);
            User user = userRepository.findByEmail(email).get();
            user.setEmailVerificationFlag(true);

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public void sendEmail(String email) throws MessagingException {
        emailVerification.sendEmail(email);
    }

    @Transactional
    public boolean modifyPassword(String email, ModifyPassword modifyPassword) {
        try {
            User user = userRepository.findByEmail(email).get();
            if (passwordEncode.passwordEncoder().encode(modifyPassword.getNowPassword())
                    .equals(user.getPassword())) {
                user.setPassword(passwordEncode.passwordEncoder().encode(modifyPassword.getModifyPassword()));
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean modifyNickname(String email, ModifyNickname modifyNickname) {
        try {
            User user = userRepository.findByEmail(email).get();
            if (passwordEncode.passwordEncoder().encode(modifyNickname.getNowPassword())
                    .equals(user.getPassword())) {
                user.setNickname(modifyNickname.getModifyNickname());
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean helpPw(FindPasswordByEmailRequest findPasswordByEmailRequest) throws MessagingException {
        Optional<User> findUser = userRepository.findByEmail(findPasswordByEmailRequest.getEmail());
        if (findUser.isPresent()) {
            User user = findUser.get();
            sendEmail(user.getEmail());

            return true;
        }

        return false;
    }

    @Transactional
    public boolean findPassword(FindPassword findPassword, String code) {
        UserEmailVerificationCode userEmailVerificationCode = emailVerificationRepository.findByVerificationCode(code);
        if (userEmailVerificationCode == null) {
            return false;
        }
        User user = userRepository.findByEmail(userEmailVerificationCode.getEmail()).get();

        user.setPassword(passwordEncode.passwordEncoder().encode(findPassword.getPassword()));

        return true;
    }
}