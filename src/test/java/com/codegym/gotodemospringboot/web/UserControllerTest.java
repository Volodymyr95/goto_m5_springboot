package com.codegym.gotodemospringboot.web;

import com.codegym.gotodemospringboot.entity.User;
import com.codegym.gotodemospringboot.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    public void testGetAllUsers() {
        List<User> users = List.of(new User(), new User(), new User());
        userRepository.saveAll(users);

        mockMvc.perform(get("/api/users/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @SneakyThrows
    public void testGetUserById() {
        User user = new User();
        user.setFirstName("Test27");
        user.setLastName("Test27");
        user.setAge(27);
        Long id = userRepository.save(user).getId();

        mockMvc.perform(get("/api/users/%s".formatted(id)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Test27")))
                .andExpect(jsonPath("$.lastName", is("Test27")))
                .andExpect(jsonPath("$.age", is(27)));
    }

    @Test
    @SneakyThrows
    public void testGetUserByIdWhenIdNotExist() {
        mockMvc.perform(get("/api/users/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void testGetUserByIdInvalid() {
        mockMvc.perform(get("/api/users/-1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void testDeleteById() {
        User user = new User();

        Long id = userRepository.save(user).getId();

        assertTrue(userRepository.existsById(id));

        mockMvc.perform(delete("/api/users/%s".formatted(id)))
                .andDo(print())
                .andExpect(status().isOk());

        assertFalse(userRepository.existsById(id));
    }

    @Test
    @SneakyThrows
    public void testDeleteByIdWithInvalidId() {

        mockMvc.perform(delete("/api/users/-1"))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @SneakyThrows
    public void testDeleteByIdWhenIdNotExist() {

        mockMvc.perform(delete("/api/users/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @SneakyThrows
    public void testCreateUser() {
        var userJson = """
                {        
                    "email": "m2157@gmail.com",
                    "age": 22   
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());

        assertNotNull(userRepository.findByEmail("m2157@gmail.com").get());
    }

    @Test
    @SneakyThrows
    public void testCreateUserWithInvalidEmail() {
        var userJson = """
                {        
                    "email": "m2157com",
                    "age": 22   
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    @SneakyThrows
    public void testUpdateUser() {
        User user = new User();
        user.setAge(20);

        Long userId = userRepository.save(user).getId();

        var userJson = """
                {   "id":%s,
                    "email": "m2157@gmail.com",
                    "age": 22   
                }
                """.formatted(userId);

        mockMvc.perform(put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());

        User dbUser = userRepository.findById(userId).get();
        assertEquals(dbUser.getAge(), 22);

    }

    @Test
    @SneakyThrows
    public void testPartialUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setAge(20);

        userRepository.save(user);

        var userJson = """
                {   "id":1,
                    "email": "m2157@gmail.com",
                    "age": 22  
                }
                """;

        mockMvc.perform(put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());

        User dbUser = userRepository.findById(1L).get();
        assertEquals(dbUser.getAge(), 22);
        assertEquals(dbUser.getFirstName(), "Test");
        assertEquals(dbUser.getLastName(), "Test");

    }
}