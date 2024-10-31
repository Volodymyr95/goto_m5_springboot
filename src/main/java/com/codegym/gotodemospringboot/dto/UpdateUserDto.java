package com.codegym.gotodemospringboot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserDto extends UserDto {
    @Min(value = 1, message = "Id cannot be less than 1")
    @NotNull
    private Long id;
}
