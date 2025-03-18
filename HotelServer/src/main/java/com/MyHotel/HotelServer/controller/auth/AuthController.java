package com.MyHotel.HotelServer.controller.auth;


import com.MyHotel.HotelServer.Services.auth.AuthService;
import com.MyHotel.HotelServer.Services.jwt.UserService;
import com.MyHotel.HotelServer.Util.JwtUtil;
import com.MyHotel.HotelServer.dto.AuthenticationRequest;
import com.MyHotel.HotelServer.dto.AuthenticationResponse;
import com.MyHotel.HotelServer.dto.SignupRequest;
import com.MyHotel.HotelServer.dto.UserDto;
import com.MyHotel.HotelServer.entity.User;
import com.MyHotel.HotelServer.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?>SignupUser(@RequestBody SignupRequest signupRequest){
        try{
            UserDto createdUser = authService.createUser(signupRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        }catch(EntityExistsException entityExistsException){
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }catch (Exception e){
            return new ResponseEntity<>(" User Not Created Please come again later", HttpStatus.BAD_REQUEST);
        }
    }





@PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){

        try{
//            System.out.println("Authenticating user: " + authenticationRequest.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
//            System.out.println("Authentication Succsessfull for: " + authenticationRequest.getEmail());

        }catch(BadCredentialsException e){
//            System.out.println("Authentication failed for: " + authenticationRequest.getEmail());

            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails= userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());

//    System.out.println("Fetching user from DB with email: " + userDetails.getUsername());
    Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
//    System.out.println("Fetched user: " + optionalUser);




    final String jwt = jwtUtil.generateToken(userDetails);
    System.out.println("Generated JWT: " + jwt);

    AuthenticationResponse authenticationResponse =new AuthenticationResponse();

        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }

        return authenticationResponse;

    }



}
