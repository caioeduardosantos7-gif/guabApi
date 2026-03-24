package com.dashapi.demo.adapter.http.auth;

import com.dashapi.demo.domain.model.AuthTokens;
import com.dashapi.demo.domain.model.Company;
import com.dashapi.demo.domain.model.User;
import com.dashapi.demo.domain.port.in.AuthUseCase;
import com.dashapi.demo.infrastructure.security.AuthenticationFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUseCase authUseCase;
    private final AuthenticationFacade authFacade;

    public AuthController(AuthUseCase authUseCase, AuthenticationFacade authFacade) {
        this.authUseCase = authUseCase;
        this.authFacade = authFacade;
    }

    record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {}
    record RefreshRequest(@NotBlank String refreshToken) {}
    record UpdateProfileRequest(
            @NotBlank String name,
            @NotBlank @Email String email,
            CompanyDto company) {}
    record CompanyDto(Long id, String name, String document, String phone) {}
    record ChangePasswordRequest(@NotBlank String currentPassword, @NotBlank String newPassword) {}

    @PostMapping("/login")
    public ResponseEntity<AuthTokens> login(@Valid @RequestBody LoginRequest req) {
        AuthTokens tokens = authUseCase.login(new AuthUseCase.LoginCommand(req.email(), req.password()));
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokens> refresh(@Valid @RequestBody RefreshRequest req) {
        AuthTokens tokens = authUseCase.refresh(new AuthUseCase.RefreshCommand(req.refreshToken()));
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> profile() {
        User current = authFacade.getCurrentUser();
        return ResponseEntity.ok(authUseCase.getProfile(current.getId()));
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@Valid @RequestBody UpdateProfileRequest req) {
        User current = authFacade.getCurrentUser();
        Company company = req.company() == null ? null
                : new Company(req.company().id(), req.company().name(),
                              req.company().document(), req.company().phone());
        User updated = authUseCase.updateProfile(
                new AuthUseCase.UpdateProfileCommand(current.getId(), req.name(), req.email(), company));
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest req) {
        User current = authFacade.getCurrentUser();
        authUseCase.changePassword(
                new AuthUseCase.ChangePasswordCommand(current.getId(), req.currentPassword(), req.newPassword()));
        return ResponseEntity.noContent().build();
    }
}
