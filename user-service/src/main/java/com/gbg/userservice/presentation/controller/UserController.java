package com.gbg.userservice.presentation.controller;

import com.gbg.userservice.application.service.UserService;
import com.gbg.userservice.presentation.dto.request.UserSignRequest;
import com.gbg.userservice.presentation.dto.response.UserResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign")
    public ResponseEntity<UUID> createUser(@RequestBody UserSignRequest userSignRequest) {

        UUID uuid = userService.createUser(userSignRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(uuid);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") UUID userId) {

        UserResponse user = userService.getUser(userId);

        return ResponseEntity.ok().body(user);
    }

}
