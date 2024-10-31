package com.codegym.gotodemospringboot.service;

import com.codegym.gotodemospringboot.dto.BasicUserDto;
import com.codegym.gotodemospringboot.dto.FullUserInfoDTO;
import com.codegym.gotodemospringboot.dto.UpdateUserDto;
import com.codegym.gotodemospringboot.dto.UserDto;
import com.codegym.gotodemospringboot.entity.User;
import com.codegym.gotodemospringboot.exceptions.EmailAlreadyInUseException;
import com.codegym.gotodemospringboot.exceptions.EntityNotFoundException;
import com.codegym.gotodemospringboot.repository.UserRepository;
import com.codegym.gotodemospringboot.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserMapper userMapper;

    public FullUserInfoDTO create(UserDto user) {
        checkIsEmailExist(user.getEmail());
        User entity = modelMapper.map(user, User.class);
        return modelMapper.map(userRepository.save(entity), FullUserInfoDTO.class);
    }


    public FullUserInfoDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with %s email not found!".formatted(email)));
        return modelMapper.map(user, FullUserInfoDTO.class);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User with %d Id not found!".formatted(id));
        }
        userRepository.deleteById(id);
    }

    public List<BasicUserDto> getByFirstNameOrLastName(String firstName, String lastName) {
        return userRepository.
                findUserByFirstNameAndLastName(firstName, lastName)
                .stream().
                map(user -> modelMapper.map(user, BasicUserDto.class))
                .collect(Collectors.toList());
    }

    public FullUserInfoDTO update(UpdateUserDto userDto) {
        var user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id: %s not found".formatted(userDto.getId())));

        if (userRepository.existsByEmailAndIdNot(userDto.getEmail(), userDto.getId())) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        userMapper.mapUserFromUserDto(userDto, user);

        return modelMapper.map(userRepository.save(user), FullUserInfoDTO.class);
    }

    public FullUserInfoDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with %s not found".formatted(id)));
        return modelMapper.map(user, FullUserInfoDTO.class);
    }

    private void checkIsEmailExist(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyInUseException("User with %s email already exists".formatted(email));
        }
    }
}