package com.MyHotel.HotelServer.dto;


//import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    private String email;
    private  String password;
    private  String name;

}
