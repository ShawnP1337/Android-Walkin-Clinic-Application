package com.example.walk_in_clinic;

import org.apache.commons.codec.digest.DigestUtils;

class User {

    //instance variables
    private String username;
    private String email;
    private String password;
    private String role;


    public User(String username, String email, String password, String role){
        this.username = username;
        this.email = email;
        this.password = DigestUtils.sha256Hex(password);
        this.role = role;
    }

    String getUsername(){
        return username;
    }
    String getEmail(){
        return email;
    }
    String getPassword(){
        return password;
    }
    String getRole(){
        return role;
    }
}
