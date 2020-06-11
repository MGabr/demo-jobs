package com.github.mgabr.demojobs.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentUserService implements UserService {

    private final UserDocumentMapper userMapper;

    private final UserDocumentRepository userRespository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public String create(UserCreateDTO user) {
        var userDoc = userMapper.toDocument(user);
        userDoc.setPassword(passwordEncoder.encode(userDoc.getPassword()));
        return userRespository.save(userDoc).getId();
    }

    @Override
    public IdUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRespository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
