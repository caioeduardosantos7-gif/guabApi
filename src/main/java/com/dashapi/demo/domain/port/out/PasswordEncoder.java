package com.dashapi.demo.domain.port.out;

public interface PasswordEncoder {
    String encode(String raw);
    boolean matches(String raw, String encoded);
}
