package com.dashapi.demo.domain.port.out;

public interface TokenService {
    String generateAccessToken(String subject);
    String generateRefreshToken(String subject);
    String extractSubject(String token);
    boolean isValid(String token);
}
