package com.MyHotel.HotelServer.Services.admin.reservation;


import com.MyHotel.HotelServer.dto.ReservationResponseDto;
import com.MyHotel.HotelServer.entity.Reservation;
import com.MyHotel.HotelServer.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements  ReservationService{

    private final ReservationRepository reservationRepository;

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


}
