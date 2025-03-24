package com.MyHotel.HotelServer.Services.admin.reservation;

import com.MyHotel.HotelServer.dto.ReservationResponseDto;

public interface ReservationService {

    ReservationResponseDto getAllReservation(int pageNumber);

    boolean changeReservationStatus(Long id, String status);
}
