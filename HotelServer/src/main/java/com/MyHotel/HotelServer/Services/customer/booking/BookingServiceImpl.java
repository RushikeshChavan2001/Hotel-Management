package com.MyHotel.HotelServer.Services.customer.booking;

import com.MyHotel.HotelServer.dto.ReservationDto;
import com.MyHotel.HotelServer.entity.Reservation;
import com.MyHotel.HotelServer.entity.Room;
import com.MyHotel.HotelServer.entity.User;
import com.MyHotel.HotelServer.enums.ReservationStatus;
import com.MyHotel.HotelServer.repository.ReservationRepository;
import com.MyHotel.HotelServer.repository.RoomRepository;
import com.MyHotel.HotelServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements  BookingService {

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final ReservationRepository reservationRepository;

    public boolean postReservation(ReservationDto reservationDto){

        Optional<User> optionalUser = userRepository.findById(reservationDto.getUserId());
        Optional<Room> optionalRoom =roomRepository.findById(reservationDto.getRoomId());

        if(optionalRoom.isPresent() && optionalUser.isPresent()){

            Reservation reservation = new Reservation();

            reservation.setRoom(optionalRoom.get());
            reservation.setUser(optionalUser.get());
            reservation.setCheckInDate(reservationDto.getCheckInDate());
            reservation.setCheckOutDate(reservationDto.getCheckOutDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);


            long days = reservationDto.getCheckOutDate().toEpochDay() - reservationDto.getCheckInDate().toEpochDay();

            reservation.setPrice(optionalRoom.get().getPrice() * days);

            reservationRepository.save(reservation);
            return true;

        }
        return false;
    }


}
