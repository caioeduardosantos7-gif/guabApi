package com.dashapi.demo.infrastructure.persistence.adapter;

import com.dashapi.demo.domain.model.User;
import com.dashapi.demo.domain.port.out.UserRepository;
import com.dashapi.demo.infrastructure.persistence.jpa.SpringUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final SpringUserRepository spring;

    public UserRepositoryAdapter(SpringUserRepository spring) {
        this.spring = spring;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return spring.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return spring.findById(id);
    }

    @Override
    public User save(User user) {
        return spring.save(user);
    }
}
