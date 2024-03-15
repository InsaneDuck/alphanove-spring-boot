package dev.insaneduck.alphanovespringboot.repositories;

import dev.insaneduck.alphanovespringboot.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(nativeQuery = false, value = "select r from Role r where r.username=:username")
    Optional<List<Role>> findRolesByUsername(@Param("username") String username);

}