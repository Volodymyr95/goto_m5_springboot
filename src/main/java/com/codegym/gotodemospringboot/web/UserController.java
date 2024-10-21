package com.codegym.gotodemospringboot.web;

import com.codegym.gotodemospringboot.dto.BasicUserDto;
import com.codegym.gotodemospringboot.dto.FullUserInfoDTO;
import com.codegym.gotodemospringboot.dto.UpdateUserDto;
import com.codegym.gotodemospringboot.dto.UserDto;
import com.codegym.gotodemospringboot.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FullUserInfoDTO create(@RequestBody @Validated UserDto user) {
        return userService.create(user);
    }

    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable @Min(value = 0, message = "Id cannot be less than 0") Long id) {
        userService.delete(id);
    }

    @GetMapping("/user/email")
    public FullUserInfoDTO getUserByEmail(@RequestParam @Email(message = "Invalid email format") String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/user")
    public List<BasicUserDto> getUserByFirstOrLastName(@RequestParam(required = false) String firstName,
                                                       @RequestParam(required = false) String lastName) {
        return userService.getByFirstNameOrLastName(firstName, lastName);

    }

    @PutMapping
    public FullUserInfoDTO update(@RequestBody UpdateUserDto userDto) {
        return userService.update(userDto);
    }

}//loclahost:8080/api/users/ POST
/*
C - POST
R - GET
U - PUT
D - DELETE
 */