package com.dashapi.demo.domain.port.in;

import com.dashapi.demo.domain.model.AuthTokens;
import com.dashapi.demo.domain.model.Company;
import com.dashapi.demo.domain.model.User;

public interface AuthUseCase {
    record LoginCommand(String email, String password) {}
    record RefreshCommand(String refreshToken) {}
    record UpdateProfileCommand(Long userId, String name, String email, Company company) {}
    record ChangePasswordCommand(Long userId, String currentPassword, String newPassword) {}

    AuthTokens login(LoginCommand command);
    AuthTokens refresh(RefreshCommand command);
    User getProfile(Long userId);
    User updateProfile(UpdateProfileCommand command);
    void changePassword(ChangePasswordCommand command);
}
