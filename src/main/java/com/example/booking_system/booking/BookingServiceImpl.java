package com.example.booking_system.booking;

import org.springframework.stereotype.Service;

import com.example.booking_system.booking.model.BookingCrudDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.LocationService;
import com.example.booking_system.location.model.location.LocationDto;
import com.example.booking_system.location.model.location.LocationEnum.RoomType;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final LocationService locationService;

    public BookingServiceImpl(BookingRepository bookingRepository, LocationService locationService) {
        this.bookingRepository = bookingRepository;
        this.locationService = locationService;
    }

    @Override
    public Long createBooking(BookingCrudDto bookingCrudDto, HeaderCollections header) {
        LocationDto location = locationService.findLocationById(bookingCrudDto.getRoomId())
                .orElseThrow(() -> new BusinessException("BOK_BOOKING_LOCATIONNOTFOUND"));
        if (!location.getRoomType().equals(RoomType.ROOM))
            throw new BusinessException("BOK_BOOKING_LOCATIONNOTROOM");

        bookingCrudDto.setRoomName(location.getName());
        bookingCrudDto.setRoomType(location.getRoomType());
        return bookingRepository.create(bookingCrudDto.toRecord(bookingCrudDto, header));
    }

}
