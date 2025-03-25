package com.MyHotel.HotelServer.Services.admin.reservation;


import com.MyHotel.HotelServer.dto.ReservationResponseDto;
import com.MyHotel.HotelServer.entity.Reservation;
import com.MyHotel.HotelServer.entity.Room;
import com.MyHotel.HotelServer.enums.ReservationStatus;
import com.MyHotel.HotelServer.repository.ReservationRepository;
import com.MyHotel.HotelServer.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements  ReservationService{

    private final ReservationRepository reservationRepository;

    private final RoomRepository roomRepository;

    public static  final int SEARCH_RESULT_PER_PAGE =4;

    public ReservationResponseDto getAllReservation(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Reservation> reservationPage = reservationRepository.findAll((pageable));

        ReservationResponseDto reservationResponseDto= new ReservationResponseDto();

        reservationResponseDto.setReservationDto(reservationPage.stream().map(Reservation::getReservationDto).toList());

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());

        reservationResponseDto.setTotalPage(reservationPage.getTotalPages());

        return reservationResponseDto;


    }

        public boolean changeReservationStatus(Long id, String status){

            Optional<Reservation> optionalReservation= reservationRepository.findById(id);

            if (optionalReservation.isPresent()){
                Reservation existingReservation = optionalReservation.get();

                if(Objects.equals(status, "APPROVE")){
                    existingReservation.setReservationStatus(ReservationStatus.APPROVE);
                }else{
                    existingReservation.setReservationStatus(ReservationStatus.REJECTED);
                }

                reservationRepository.save(existingReservation);
                Room existingRoom = existingReservation.getRoom();

                existingRoom.setAvailable(false);

                roomRepository.save(existingRoom);

                return  true;

            }
            return  false;
        }




}
