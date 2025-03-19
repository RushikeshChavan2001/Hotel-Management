package com.MyHotel.HotelServer.Services.admin.rooms;

import com.MyHotel.HotelServer.dto.RoomDto;
import com.MyHotel.HotelServer.dto.RoomsResponseDto;
import com.MyHotel.HotelServer.entity.Room;
import com.MyHotel.HotelServer.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class RoomsServiceImpl implements  RoomsService{
    private final RoomRepository roomRepository;

    public boolean postRoom(RoomDto roomDto){
        try {
            Room room= new Room();
            room.setName(roomDto.getName());
            room.setPrice(roomDto.getPrice());
            room.setType(roomDto.getType());
            room.setAvailable(true);

            roomRepository.save(room);
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    public RoomsResponseDto getAllRooms(int pageNumber){
        Pageable pageble = PageRequest.of(pageNumber,6);
        Page<Room> roomPage = roomRepository.findAll(pageble);

        RoomsResponseDto roomsResponseDto= new RoomsResponseDto();
        roomsResponseDto.setPageNumber(roomPage.getPageable().getPageNumber());
        roomsResponseDto.setTotalPages(roomPage.getTotalPages());
        roomsResponseDto.setRoomDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));

        return  roomsResponseDto;
    }

}
