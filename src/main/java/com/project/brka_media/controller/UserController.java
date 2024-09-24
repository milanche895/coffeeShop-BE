package com.project.brka_media.controller;

import com.project.brka_media.DTO.UserDTO;
import com.project.brka_media.constants.WebConstants;
import com.project.brka_media.entity.UserEntity;
import com.project.brka_media.repository.UserRepository;
import com.project.brka_media.security.AuthenticationResponse;
import com.project.brka_media.security.CustomUserDetailsService;
import com.project.brka_media.security.JwtUtil;
import com.project.brka_media.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="User")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(WebConstants.PUBLIC_BASE_URL +"/login")
    public ResponseEntity<?> login(@RequestBody UserEntity userEntity)
            throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userEntity.getEmail(), userEntity.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        UserDetails userdetails = userDetailsService.loadUserByUsername(userEntity.getEmail());
        UserEntity userFromDB = userRepository.findByEmail(userEntity.getEmail());
        String token = "Bearer " + jwtUtil.generateToken(userdetails, userFromDB);

        return ResponseEntity.ok(new AuthenticationResponse(token, userdetails.getAuthorities()));
    }
    @GetMapping(WebConstants.PUBLIC_BASE_URL + "/user")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return new ResponseEntity<List<UserDTO>>(userService.getAllUser(), HttpStatus.OK);
    }
    @PutMapping(WebConstants.BASE_CLIENT_URL + "/user/{id}")
    public ResponseEntity<UserDTO> updateUser( @PathVariable("id") Long id,@RequestBody UserDTO userDTO) {

        return new ResponseEntity<UserDTO>(userService.updateUser(userDTO, id),HttpStatus.OK);
    }
    @GetMapping(WebConstants.BASE_CLIENT_URL + "/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) {

        return new ResponseEntity<UserDTO>(userService.getUserById(id), HttpStatus.OK);
    }
    @GetMapping(WebConstants.BASE_ADMIN_URL + "/user/client")
    public ResponseEntity<UserDTO> getUserByUsername(@RequestParam(name = "username", required = false) String username) {

        return new ResponseEntity<UserDTO>(userService.getUserByUsername(username), HttpStatus.OK);
    }
}
