package com.codegym.gotodemospringboot.security.service;

import com.codegym.gotodemospringboot.dto.FullUserInfoDTO;
import com.codegym.gotodemospringboot.dto.SignInDto;
import com.codegym.gotodemospringboot.dto.SignInResponseDto;
import com.codegym.gotodemospringboot.dto.SignUpDto;
import com.codegym.gotodemospringboot.entity.User;
import com.codegym.gotodemospringboot.exceptions.EmailAlreadyInUseException;
import com.codegym.gotodemospringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public FullUserInfoDTO signUp(SignUpDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyInUseException("Email %s already exist".formatted(userDto.getEmail()));
        }

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return modelMapper.map(userRepository.save(user), FullUserInfoDTO.class);
    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDto.getEmail(), signInDto.getPassword()
        ));

        return new SignInResponseDto(jwtService.generateToken(userRepository.findByEmail(signInDto.getEmail()).get()));
    }
}
