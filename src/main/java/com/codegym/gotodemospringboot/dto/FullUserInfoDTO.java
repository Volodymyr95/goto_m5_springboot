package com.codegym.gotodemospringboot.dto;

import lombok.Data;

@Data
public class FullUserInfoDTO {
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private int age;

}
