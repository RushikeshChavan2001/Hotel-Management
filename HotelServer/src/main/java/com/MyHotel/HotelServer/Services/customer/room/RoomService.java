package com.MyHotel.HotelServer.Services.customer.room;

import com.MyHotel.HotelServer.dto.RoomsResponseDto;
import org.springframework.stereotype.Service;

public interface RoomService {
    RoomsResponseDto getAvailableRooms(int pageNumber);
}
