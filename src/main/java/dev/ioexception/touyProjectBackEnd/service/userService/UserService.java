package dev.ioexception.touyProjectBackEnd.service.userService;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean findUser(String email);

}
