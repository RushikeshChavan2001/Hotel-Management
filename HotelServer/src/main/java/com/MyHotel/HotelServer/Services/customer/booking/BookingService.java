package com.MyHotel.HotelServer.Services.customer.booking;


import com.MyHotel.HotelServer.dto.ReservationDto;

public interface BookingService {

    boolean postReservation(ReservationDto reservationDto);
}
