package com.MyHotel.HotelServer.Services.auth;


import com.MyHotel.HotelServer.dto.SignupRequest;
import com.MyHotel.HotelServer.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);
}
