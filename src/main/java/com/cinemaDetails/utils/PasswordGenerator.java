package com.cinemaDetails.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("Dreambig"));
        System.out.println(passwordEncoder.encode("ajay"));
        System.out.println(passwordEncoder.encode("kumar"));
    }
}
