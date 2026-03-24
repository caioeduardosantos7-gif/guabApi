package com.dashapi.demo.application;

import com.dashapi.demo.domain.model.AuthTokens;
import com.dashapi.demo.domain.model.Company;
import com.dashapi.demo.domain.model.User;
import com.dashapi.demo.domain.port.in.AuthUseCase;
import com.dashapi.demo.domain.port.out.CompanyRepository;
import com.dashapi.demo.domain.port.out.PasswordEncoder;
import com.dashapi.demo.domain.port.out.TokenService;
import com.dashapi.demo.domain.port.out.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService implements AuthUseCase {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository,
                       CompanyRepository companyRepository,
                       PasswordEncoder passwordEncoder,
                       TokenService tokenService) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public AuthTokens login(LoginCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return buildTokens(user.getEmail());
    }

    @Override
    public AuthTokens refresh(RefreshCommand command) {
        if (!tokenService.isValid(command.refreshToken())) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        String email = tokenService.extractSubject(command.refreshToken());
        return buildTokens(email);
    }

    @Override
    @Transactional(readOnly = true)
    public User getProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public User updateProfile(UpdateProfileCommand command) {
        User existing = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        existing.setName(command.name());
        existing.setEmail(command.email());

        if (command.company() != null) {
            Company saved = companyRepository.save(command.company());
            existing.setCompany(saved);
        }

        return userRepository.save(existing);
    }

    @Override
    public void changePassword(ChangePasswordCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!passwordEncoder.matches(command.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(command.newPassword()));
        userRepository.save(user);
    }

    private AuthTokens buildTokens(String email) {
        return new AuthTokens(
                tokenService.generateAccessToken(email),
                tokenService.generateRefreshToken(email)
        );
    }
}
