package com.MyHotel.HotelServer.dto;

import com.MyHotel.HotelServer.enums.ReservationStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationDto {
    private Long id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Long price;

    private ReservationStatus reservationStatus;

    private Long roomId;
    private String roomType;

    private String roomName;

    private Long userId;

    private String userName;


}
