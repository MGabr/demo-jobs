package com.github.mgabr.demojobs.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    private final IdUserDetailsMapper userDetailsMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody UserCreateDTO user, HttpServletRequest request) throws ServletException {
        var userId = userService.create(user);
        request.login(user.getEmail(), user.getPassword()); // auto login after user creation
        return userId;
    }

    @GetMapping("/me")
    public UserDTO user(@AuthenticationPrincipal IdUserDetails user) {
        return userDetailsMapper.toDTO(user);
    }
}
