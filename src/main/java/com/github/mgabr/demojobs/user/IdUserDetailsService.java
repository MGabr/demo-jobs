package com.github.mgabr.demojobs.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IdUserDetailsService extends UserDetailsService {

    @Override
    IdUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
