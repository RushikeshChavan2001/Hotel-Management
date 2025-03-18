package com.MyHotel.HotelServer.Services.auth;


import com.MyHotel.HotelServer.dto.SignupRequest;
import com.MyHotel.HotelServer.dto.UserDto;
import com.MyHotel.HotelServer.entity.User;
import com.MyHotel.HotelServer.enums.UserRole;
import com.MyHotel.HotelServer.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
//import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl  implements AuthService {

    private final UserRepository userRepository;


    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> AdminAccount =userRepository.findByUserRole(UserRole.ADMIN);

        if(AdminAccount.isEmpty()){
           User user =new User();
            user.setEmail("admin@gmail.com");
            user.setName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("Admin"));

            userRepository.save(user);
            System.out.println("Admin account created successfully");
        }else {
            System.out.println("Admin Alredy exist");
        }



    }

    public UserDto createUser(SignupRequest signupRequest){
        if(userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()){
            throw  new EntityExistsException("User alredy present "+ signupRequest.getEmail());
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));

        User createdUser= userRepository.save(user);
        return createdUser.getUserDto();
    }


}
