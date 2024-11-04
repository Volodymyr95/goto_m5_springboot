package com.codegym.gotodemospringboot.repository;

import com.codegym.gotodemospringboot.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    //select u from User u where u.username = ?
    Optional<User> findByEmail(String email);

    @Query("""
                    SELECT u FROM User u 
                    WHERE (COALESCE(:firstName, '') = '' OR u.firstName = : firstName )
                    AND (COALESCE(:lastName, '') = '' OR u.lastName = : lastName )
                    
            """)
    List<User> findUserByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByEmail(String email);

}
