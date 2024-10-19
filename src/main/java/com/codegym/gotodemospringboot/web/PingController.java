package com.codegym.gotodemospringboot.web;

import com.codegym.gotodemospringboot.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ping")
@RequiredArgsConstructor
public class PingController {

    private final EntityManager entityManager;

    @GetMapping
    public String ping(@RequestParam Long userId) {

        User user = entityManager.find(User.class, userId);
        if (null == user) {
            return "User not found";
        }
        return user.toString();
    }


}
