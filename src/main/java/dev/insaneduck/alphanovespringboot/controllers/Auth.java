package dev.insaneduck.alphanovespringboot.controllers;

import dev.insaneduck.alphanovespringboot.dto.UserDetails;
import dev.insaneduck.alphanovespringboot.dto.UserLogin;
import dev.insaneduck.alphanovespringboot.dto.UserSignUp;
import dev.insaneduck.alphanovespringboot.dto.UserSignUpResponse;
import dev.insaneduck.alphanovespringboot.entities.Role;
import dev.insaneduck.alphanovespringboot.entities.User;
import dev.insaneduck.alphanovespringboot.entities.UserType;
import dev.insaneduck.alphanovespringboot.repositories.RoleRepository;
import dev.insaneduck.alphanovespringboot.repositories.UserRepository;
import dev.insaneduck.alphanovespringboot.security.JwtGenerator;
import dev.insaneduck.alphanovespringboot.services.CustomUserDetailsService;
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
@RequestMapping("/auth")
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


    @PostMapping("/login")
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

    @PostMapping("/register")
    public ResponseEntity<UserSignUpResponse> register(@RequestBody UserSignUp userSignUp) {
        if (userRepository.existsUserByUsername(userSignUp.getUserName()) && userRepository.existsUserByEmail(userSignUp.getEmail())) {
            return new ResponseEntity<>(new UserSignUpResponse(false, true, true, "user with username and email already exists"), HttpStatus.CONFLICT);
        } else if (userRepository.existsUserByEmail(userSignUp.getEmail())) {
            return new ResponseEntity<>(new UserSignUpResponse(false, false, true, "user email already exists"), HttpStatus.CONFLICT);
        } else if (userRepository.existsUserByUsername(userSignUp.getUserName())) {
            return new ResponseEntity<>(new UserSignUpResponse(false, true, false, "user with username already exists"), HttpStatus.CONFLICT);

        }
        User user = new User();
        user.setEmail(userSignUp.getEmail());
        user.setUsername(userSignUp.getUserName());
        user.setPassword(passwordEncoder.encode(userSignUp.getPassword()));
        user.setFirstName(userSignUp.getFirstName());
        user.setLastName(userSignUp.getLastName());
        user.setEnabled(true);
        userRepository.save(user);

        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role.setUsername(user);
        roleRepository.save(role);

        return new ResponseEntity<>(new UserSignUpResponse(true, false, false, "user sign up successful"), HttpStatus.OK);
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
