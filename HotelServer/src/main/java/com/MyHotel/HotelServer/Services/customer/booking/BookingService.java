package com.MyHotel.HotelServer.Services.customer.booking;


import com.MyHotel.HotelServer.dto.ReservationDto;
import com.MyHotel.HotelServer.dto.ReservationResponseDto;

public interface BookingService {

    boolean postReservation(ReservationDto reservationDto);
    ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber);
}
