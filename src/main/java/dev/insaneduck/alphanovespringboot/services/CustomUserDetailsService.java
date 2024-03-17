package dev.insaneduck.alphanovespringboot.services;

import dev.insaneduck.alphanovespringboot.entities.Role;
import dev.insaneduck.alphanovespringboot.entities.User;
import dev.insaneduck.alphanovespringboot.entities.UserType;
import dev.insaneduck.alphanovespringboot.repositories.RoleRepository;
import dev.insaneduck.alphanovespringboot.repositories.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    @Setter
    private UserType userType;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    //todo edit exception messages
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(""));
        List<Role> roles = roleRepository.findRolesByUsername(username).orElseThrow(() -> new RuntimeException(""));

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        roles.forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getRole()))
        );

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
