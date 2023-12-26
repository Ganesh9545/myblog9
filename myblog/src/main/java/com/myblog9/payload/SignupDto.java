package com.myblog9.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    private String email;
    private  String name;
    private  String username;
    private String password;



}
