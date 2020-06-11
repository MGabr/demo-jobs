package com.github.mgabr.demojobs.user;

public interface UserService extends IdUserDetailsService {

    String create(UserCreateDTO user);
}
