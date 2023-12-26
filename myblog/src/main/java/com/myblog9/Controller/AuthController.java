package com.myblog9.Controller;

import com.myblog9.entity.Role;
import com.myblog9.entity.User;
import com.myblog9.payload.JWTAuthResponse;
import com.myblog9.payload.LoginDto;
import com.myblog9.payload.SignupDto;
import com.myblog9.repository.RoleRepository;
import com.myblog9.repository.UserRepository;
import com.myblog9.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/api/auth")
public class AuthController {



    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> RegisterUser(@RequestBody SignupDto signupDto){
        if(userRepository.existsByEmail(signupDto.getEmail())){
            new ResponseEntity<>("email already exists:"+signupDto.getEmail(), HttpStatus.OK);
        }
        if(userRepository.existsByUsername(signupDto.getUsername())){
            new ResponseEntity<>("Username already exists:"+signupDto.getUsername(), HttpStatus.OK);
        }
        User user=new User();
        user.setEmail(signupDto.getEmail());
        user.setName(signupDto.getName());
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        Set<Role> role=new HashSet<>();
        role.add(roles);
        user.setRoles(role);

        userRepository.save(user);
        return  new ResponseEntity<>("user is now registered",HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }



}
