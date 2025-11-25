package com.gbg.userservice.domain.repository;

import com.gbg.userservice.domain.entity.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    UUID create(User user);

    Optional<User> findById(UUID userId);
}
