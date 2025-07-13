package com.example.booking_system.booking;

import org.springframework.stereotype.Service;

import com.example.booking_system.booking.model.BookingCrudDto;
import com.example.booking_system.header.HeaderCollections;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Long createBooking(BookingCrudDto bookingCrudDto, HeaderCollections header) {
        return bookingRepository.create(bookingCrudDto.toRecord(bookingCrudDto, header));
    }

}
