package com.blog.Util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MainClass {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("testing"));
    }
}