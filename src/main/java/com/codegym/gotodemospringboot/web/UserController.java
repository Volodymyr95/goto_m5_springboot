package com.codegym.gotodemospringboot.web;

import com.codegym.gotodemospringboot.dto.BasicUserDto;
import com.codegym.gotodemospringboot.dto.FullUserInfoDTO;
import com.codegym.gotodemospringboot.dto.UpdateUserDto;
import com.codegym.gotodemospringboot.dto.UserDto;
import com.codegym.gotodemospringboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Min(value = 0, message = "Id cannot be less than 0") Long id) {
        userService.delete(id);
    }

    @GetMapping("/user/email")
    public FullUserInfoDTO getUserByEmail(@RequestParam @Email(message = "Invalid email format") String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/")
    public List<BasicUserDto> getUserByFirstOrLastName(@RequestParam(required = false) String firstName,
                                                       @RequestParam(required = false) String lastName) {
        return userService.getByFirstNameOrLastName(firstName, lastName);
    }

    @PutMapping
    public FullUserInfoDTO update(@RequestBody UpdateUserDto userDto) {
        return userService.update(userDto);
    }

    @Operation(summary = "Get User By ID API", tags = {"user"})
    @ApiResponses(
            {@ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)})}
    )
    @GetMapping("/{id}")
    public FullUserInfoDTO getById(@PathVariable @Min(value = 0, message = "Id cannot be less than 0") Long id) {
        return userService.getById(id);
    }

}
