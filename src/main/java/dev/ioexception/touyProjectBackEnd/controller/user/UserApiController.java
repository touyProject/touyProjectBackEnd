package dev.ioexception.touyProjectBackEnd.controller.user;

import dev.ioexception.touyProjectBackEnd.dto.User.findPW.FindPassword;
import dev.ioexception.touyProjectBackEnd.dto.User.findPW.FindPasswordByEmailRequest;
import dev.ioexception.touyProjectBackEnd.dto.User.login.UserRequest;
import dev.ioexception.touyProjectBackEnd.dto.User.modifyUserInfo.ModifyNickname;
import dev.ioexception.touyProjectBackEnd.dto.User.modifyUserInfo.ModifyPassword;
import dev.ioexception.touyProjectBackEnd.dto.User.security.jwt.TokenInfo;
import dev.ioexception.touyProjectBackEnd.dto.User.signup.UserSignupRequest;
import dev.ioexception.touyProjectBackEnd.entity.User;
import dev.ioexception.touyProjectBackEnd.service.userService.UserService;
import dev.ioexception.touyProjectBackEnd.util.SecurityUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserApiController {

    private final UserService userService;

    @GetMapping("/")
    public String getIndex() {

        return "index";
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody UserRequest userRequest) {
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();

        return userService.login(email, password);
    }

//    @PostMapping("/refreshToken")
//    public TokenInfo refreshToken(@RequestBody String refreshToken) {
//
//    }

    @GetMapping("/signup")
    public String getSignup() {
        if (SecurityUtil.getCurrentUser().isAuthenticated()) {
            return "index";
        }
        return "sign-up";
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> postSignup(@RequestBody UserSignupRequest userSignupRequest) throws Exception {
        if (!userService.signup(userSignupRequest)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/verificationEmail")
    public void verification(@RequestParam(value = "code") String code) {
        userService.validationEmail(code);
    }

    // ------------------------------------------------------------------------------------------------

    @GetMapping("/modifyPassword")
    public String getModifyPassword() {

        return "user/modifyPassword";
    }

    @PatchMapping("/modifyPassword")
    public ResponseEntity<HttpStatus> patchModifyPassword(ModifyPassword modifyPassword) {
        String email = SecurityUtil.getCurrentUser().getName();
        if (userService.modifyPassword(email, modifyPassword)) {

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/modifyNickname")
    public String getModifyNickname() {

        return "user/modifyPassword";
    }

    @PatchMapping("/modifyNickname")
    public ResponseEntity<HttpStatus> patchModifyNickname(ModifyNickname modifyNickname) {
        String email = SecurityUtil.getCurrentUser().getName();
        if (userService.modifyNickname(email, modifyNickname)) {

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/helppw")
    public String getSearchPassword() {

        return "user/searchPassword";
    }

    @PostMapping("/helppw")
    public ResponseEntity<HttpStatus> postSearchPassword(
            @RequestBody FindPasswordByEmailRequest findPasswordByEmailRequest)
            throws MessagingException {
        if (userService.helpPw(findPasswordByEmailRequest)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/findpassword")
    public ResponseEntity<HttpStatus> getFindPassword(@RequestParam(value = "code") String code) {
        if (!userService.validationEmail(code)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/findpassword")
    public ResponseEntity<HttpStatus> postFindPassword(@RequestBody FindPassword findPassword,
                                                       @RequestParam(value = "code") String code) {
        if (userService.findPassword(findPassword, code)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/finduser")
    public ResponseEntity<User> findUser() {
        String email = SecurityUtil.getCurrentUser().getName();
        Optional<User> user = userService.findUser(email);

        return ResponseEntity.ok(user.get());
    }


}
