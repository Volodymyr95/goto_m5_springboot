package com.codegym.gotodemospringboot.web;

import com.codegym.gotodemospringboot.dto.FullUserInfoDTO;
import com.codegym.gotodemospringboot.dto.SignInDto;
import com.codegym.gotodemospringboot.dto.SignInResponseDto;
import com.codegym.gotodemospringboot.dto.SignUpDto;
import com.codegym.gotodemospringboot.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public FullUserInfoDTO signUp(@RequestBody @Validated SignUpDto dto) {
        return authService.signUp(dto);
    }

    @PostMapping("/sign-in")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) {
        return authService.signIn(signInDto);
    }


}
