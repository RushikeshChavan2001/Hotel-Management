package com.MyHotel.HotelServer.dto;


import com.MyHotel.HotelServer.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;

    private Long userId;

    private UserRole userRole;


}
