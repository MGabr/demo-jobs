package com.github.mgabr.demojobs.user;

import org.springframework.security.core.userdetails.UserDetails;

public interface IdUserDetails extends UserDetails {

    String getId();
}
