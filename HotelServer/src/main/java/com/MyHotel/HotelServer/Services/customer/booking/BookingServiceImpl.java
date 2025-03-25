package com.MyHotel.HotelServer.Services.customer.booking;

import com.MyHotel.HotelServer.dto.ReservationDto;
import com.MyHotel.HotelServer.dto.ReservationResponseDto;
import com.MyHotel.HotelServer.entity.Reservation;
import com.MyHotel.HotelServer.entity.Room;
import com.MyHotel.HotelServer.entity.User;
import com.MyHotel.HotelServer.enums.ReservationStatus;
import com.MyHotel.HotelServer.repository.ReservationRepository;
import com.MyHotel.HotelServer.repository.RoomRepository;
import com.MyHotel.HotelServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements  BookingService {

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final ReservationRepository reservationRepository;


    public static final int  SEARCH_RESULT_PER_PAGE = 4 ;

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


    public ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Reservation> reservationPage = reservationRepository.findAllByUserId(pageable, userId);

        ReservationResponseDto reservationResponseDto= new ReservationResponseDto();

        reservationResponseDto.setReservationDto(reservationPage.stream().map(Reservation::getReservationDto).toList());

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());

        reservationResponseDto.setTotalPage(reservationPage.getTotalPages());

        return reservationResponseDto;

    }


}
