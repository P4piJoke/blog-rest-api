package com.pp4jk.blogrestapi.controller;

import com.pp4jk.blogrestapi.dto.JWTAuthResponse;
import com.pp4jk.blogrestapi.dto.LoginDto;
import com.pp4jk.blogrestapi.dto.RegisterDto;
import com.pp4jk.blogrestapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(
        name = "Auth REST APIs"
)
public class AuthController {

    private final AuthService service;

    @Operation(
            summary = "User Login REST API",
            description = "Login into Blog REST API using user details from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESSFUL"
    )
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = service.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @Operation(
            summary = "User Registration REST API",
            description = "User Registration into Blog REST API and store user data into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = service.register(registerDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
