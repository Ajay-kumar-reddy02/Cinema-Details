package com.cinemaDetails.security;

import com.cinemaDetails.entity.Visitors;
import com.cinemaDetails.repository.VisitorsRepo;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

//Helps to use the users and roles from the database
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private VisitorsRepo visitorsRepo;

    public CustomUserDetailsService(VisitorsRepo visitorsRepo) {
        this.visitorsRepo = visitorsRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Visitors visitor = visitorsRepo.findByUsername(username).orElseThrow(
                () -> new BadCredentialsException("Username or Password Incorrect"));

        Set<GrantedAuthority> authoritySet = visitor.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        //User is an Implementation of UserDetails
        return new User(visitor.getUsername(), visitor.getPassword(), authoritySet);
    }
}
