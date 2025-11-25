package com.gbg.userservice.infrastructure.repository;

import com.gbg.userservice.domain.entity.User;
import com.gbg.userservice.domain.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public UUID create(User user) {

        userJpaRepository.save(user);
        return user.getId();
    }

    @Override
    public Optional<User> findById(UUID userId) {

        return userJpaRepository.findById(userId);
    }
}
