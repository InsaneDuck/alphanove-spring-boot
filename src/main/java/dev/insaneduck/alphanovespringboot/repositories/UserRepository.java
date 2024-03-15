package dev.insaneduck.alphanovespringboot.repositories;

import dev.insaneduck.alphanovespringboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(nativeQuery = false, value = "select u from User u where u.username=:username")
    Optional<User> findUserByUsername(@Param("username") String username);

    
    boolean existsUserByUsername(@Param("username") String username);
}