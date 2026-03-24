package com.dashapi.demo.infrastructure.security;

import com.dashapi.demo.domain.port.out.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder();

    @Override
    public String encode(String raw) {
        return delegate.encode(raw);
    }

    @Override
    public boolean matches(String raw, String encoded) {
        return delegate.matches(raw, encoded);
    }
}
