package com.pp4jk.blogrestapi.service;

import com.pp4jk.blogrestapi.dto.LoginDto;
import com.pp4jk.blogrestapi.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
