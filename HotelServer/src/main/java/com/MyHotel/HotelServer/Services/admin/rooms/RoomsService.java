package com.MyHotel.HotelServer.Services.admin.rooms;


import com.MyHotel.HotelServer.dto.RoomDto;
import com.MyHotel.HotelServer.dto.RoomsResponseDto;

public interface RoomsService {
    boolean postRoom(RoomDto roomDto);

    RoomsResponseDto getAllRooms(int pageNumber);
    RoomDto getRoomById(Long id);

    boolean updateRoom(Long id, RoomDto roomDto);

   void  deleteRoom(Long id);

}
