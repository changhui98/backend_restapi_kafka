package com.gbg.userservice.application.service;

import com.gbg.userservice.domain.entity.User;
import com.gbg.userservice.domain.repository.UserRepository;
import com.gbg.userservice.presentation.dto.request.UserSignRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UUID createUser(UserSignRequest userSignRequest) {

        User user = User.of(userSignRequest.username(), userSignRequest.password());

        return userRepository.create(user);
    }
}
