package com.MyHotel.HotelServer.repository;

import com.MyHotel.HotelServer.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
