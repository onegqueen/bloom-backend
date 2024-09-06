package com.example.bloombackend.restdocstest.controller;

import com.example.bloombackend.restdocstest.controller.dto.request.UserTestRequest;
import com.example.bloombackend.restdocstest.controller.dto.response.UserTestResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/users")
public class RestDocsTestController {

    @PostMapping
    public UserTestResponse createTestUser(@RequestBody UserTestRequest user) {
        return new UserTestResponse(user.name(), user.age());
    }
}