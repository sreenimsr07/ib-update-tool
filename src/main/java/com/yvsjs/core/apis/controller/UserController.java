package com.yvsjs.core.apis.controller;

import com.yvsjs.core.apis.dto.User;
import com.yvsjs.core.apis.requests.UserCreationRequest;
import com.yvsjs.core.apis.response.UserCreationResponse;
import com.yvsjs.core.apis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/createUser")
    public UserCreationResponse createUser(@RequestBody UserCreationRequest userCreationRequest) {
        return userService.createUser(userCreationRequest);

    }


    @GetMapping(value = "/{identification}")
    public ResponseEntity<User> readUser(@PathVariable("identification") String identification) {
        return ResponseEntity.ok(userService.readUser(identification));
    }

    @GetMapping
    public ResponseEntity<List<User>> readUser(Pageable pageable) {
        return ResponseEntity.ok(userService.readUsers(pageable));
    }

}

