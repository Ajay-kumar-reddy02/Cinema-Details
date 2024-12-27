package com.cinemaDetails.controller;

import com.cinemaDetails.payload.JWTResponseDTo;
import com.cinemaDetails.payload.VisitorsDTO;
import com.cinemaDetails.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    //To signin the user
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTResponseDTo> login(@Valid @RequestBody VisitorsDTO visitorsDTO){
        String token = authenticationService.checkVisitor(visitorsDTO);

        JWTResponseDTo jwt = new JWTResponseDTo();
        jwt.setToken(token);

        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    //To signup the user
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@Valid @RequestBody VisitorsDTO visitorsDTO){
        String message = authenticationService.registerVisitor(visitorsDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
