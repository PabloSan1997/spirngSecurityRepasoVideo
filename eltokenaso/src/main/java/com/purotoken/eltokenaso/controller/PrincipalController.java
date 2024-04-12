package com.purotoken.eltokenaso.controller;

import org.springframework.web.bind.annotation.RestController;

import com.purotoken.eltokenaso.controller.request.CreateUserDto;
import com.purotoken.eltokenaso.models.ERole;
import com.purotoken.eltokenaso.models.RoleEntity;
import com.purotoken.eltokenaso.models.UserEntity;
import com.purotoken.eltokenaso.repository.UserRepository;

import jakarta.validation.Valid;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class PrincipalController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
   
    @GetMapping("/hello") 
    public String hello(){
        return "Hello world not secured";
    }

    @GetMapping("/helloSecured") 
    public String helloSecired(){
        return "Hello world secured";
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDto createUserDto){
        
        Set<RoleEntity> roles = createUserDto.getRoles()
        .stream()
        .map(role -> RoleEntity.builder().name(ERole.valueOf(role)).build()).collect(Collectors.toSet());
        String password = passwordEncoder.encode(createUserDto.getPassword());
        UserEntity userEntity = UserEntity.builder()
        .username(createUserDto.getUsername())
        .email(createUserDto.getEmail())
        .password(password)
        .roles(roles).build();
        userRepository.save(userEntity);
        return ResponseEntity.status(201).body(userEntity);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "Se ha borrado usuario";
    }
}
