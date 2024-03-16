package dev.insaneduck.alphanovespringboot.controllers;

import dev.insaneduck.alphanovespringboot.dto.UserDetails;
import dev.insaneduck.alphanovespringboot.dto.UserLogin;
import dev.insaneduck.alphanovespringboot.entities.Role;
import dev.insaneduck.alphanovespringboot.entities.User;
import dev.insaneduck.alphanovespringboot.entities.UserType;
import dev.insaneduck.alphanovespringboot.repositories.RoleRepository;
import dev.insaneduck.alphanovespringboot.repositories.UserRepository;
import dev.insaneduck.alphanovespringboot.security.CustomUserDetailsService;
import dev.insaneduck.alphanovespringboot.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class Auth {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private RoleRepository roleRepository;


    @PostMapping("auth")
    public ResponseEntity<UserDetails> login(@RequestBody UserLogin userLogin) {
        if (userRepository.existsUserByUsername(userLogin.getUsername())) {
            Optional<User> user = userRepository.findUserByUsername(userLogin.getUsername());
            Optional<List<Role>> role = roleRepository.findRolesByUsername(userLogin.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication, getHighestRole(role.get()));
            if (user.isPresent()) {
                return new ResponseEntity<>(UserDetails.userToUserDetails(user.get(), token), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    String getHighestRole(List<Role> roles) {
        for (Role role : roles) {
            String r = role.getRole();

            if (Objects.equals(r, UserType.ROLE_ADMIN.toString())) {
                return UserType.ROLE_ADMIN.toString();
            } else if (Objects.equals(r, UserType.ROLE_SUPPLIER.toString())) {
                return UserType.ROLE_CONSUMER.toString();
            } else if (Objects.equals(r, UserType.ROLE_CONSUMER.toString())) {
                return UserType.ROLE_CONSUMER.toString();
            }
        }
        return null;
    }
}
