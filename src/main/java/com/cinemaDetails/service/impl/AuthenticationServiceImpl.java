package com.cinemaDetails.service.impl;

import com.cinemaDetails.entity.Roles;
import com.cinemaDetails.entity.Visitors;
import com.cinemaDetails.exception.DetailsAlreadyExists;
import com.cinemaDetails.payload.VisitorsDTO;
import com.cinemaDetails.repository.RolesRepo;
import com.cinemaDetails.repository.VisitorsRepo;
import com.cinemaDetails.security.JWTTokenProvider;
import com.cinemaDetails.service.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private AuthenticationManager authenticationManager;
    private VisitorsRepo visitorsRepo;
    private RolesRepo rolesRepo;
    private PasswordEncoder passwordEncoder;
    private JWTTokenProvider jwtTokenProvider;
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     VisitorsRepo visitorsRepo,
                                     PasswordEncoder passwordEncoder,
                                     RolesRepo rolesRepo,
                                     JWTTokenProvider jwtTokenProvider){
        this.authenticationManager = authenticationManager;
        this.visitorsRepo = visitorsRepo;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepo = rolesRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //Helps to check the user exists in database or not
    @Override
    public String checkVisitor(VisitorsDTO visitorsDTO) {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(visitorsDTO.getUsername(),
                    visitorsDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtTokenProvider.generateJWTToken(authenticate);
        return token;
    }

    //Helps to register the user
    @Override
    public String registerVisitor(VisitorsDTO visitorsDTO) {
        if(visitorsRepo.existsByUsernameIgnoreCase(visitorsDTO.getUsername())){
            throw new DetailsAlreadyExists("Choose different username");
        }
        Visitors newUser = new Visitors();
        newUser.setUsername(visitorsDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(visitorsDTO.getPassword()));
        Set<Roles> rolesSet = new HashSet<>();
        Roles roleUser = rolesRepo.findByName("ROLE_USER").get();
        rolesSet.add(roleUser);
        newUser.setRoles(rolesSet);
        visitorsRepo.save(newUser);
        return "User Signed Up Successfully";
    }
}
