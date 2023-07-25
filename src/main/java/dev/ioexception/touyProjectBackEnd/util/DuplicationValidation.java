package dev.ioexception.touyProjectBackEnd.util;

import dev.ioexception.touyProjectBackEnd.entity.User;
import dev.ioexception.touyProjectBackEnd.repository.userRepository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DuplicationValidation {
    private final UserRepository userRepository;

    public boolean isEmailDuplication(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.isEmpty();
    }

    public boolean isNicknameDuplication(String nickname) {
        User user = userRepository.findByNickname(nickname);

        return user == null;
    }
}
