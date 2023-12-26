package com.myblog9.repository;

import com.myblog9.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;


public  interface UserRepository extends JpaRepository<User,Long>{
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);



    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username,String email);


}

