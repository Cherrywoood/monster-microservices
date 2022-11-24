package com.example.userservice.controller;

import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.mapper.PageMapper;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;
@RequiredArgsConstructor
@Validated
@RestController
@Slf4j
@RequestMapping("/users-service/users")
public class UserServiceController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserResponseDTO> findById(@PathVariable UUID id) {
        return userService.findById(id).map(userMapper::toUserResponseDto);
    }
}
