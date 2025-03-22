package com.MyHotel.HotelServer.repository;

import com.MyHotel.HotelServer.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {

    Page<Room> findByAvailable(boolean available, Pageable pageable);
}
