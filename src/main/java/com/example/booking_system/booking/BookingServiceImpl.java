package com.example.booking_system.booking;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.booking_system.booking.model.BookingCrudDto;
import com.example.booking_system.event.EventService;
import com.example.booking_system.event.model.EventDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.location.LocationService;
import com.example.booking_system.location.seat_history.SeatHistoryService;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryCrudDto;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryDto;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryEnum.SeatHistoryStatus;
import com.example.booking_system.sequence.SequenceService;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final LocationService locationService;
    private final SeatHistoryService seatHistoryService;
    private final EventService eventService;
    private final SequenceService sequenceService;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            LocationService locationService,
            SeatHistoryService seatHistoryService,
            EventService eventService,
            SequenceService sequenceService
            ) {
        this.bookingRepository = bookingRepository;
        this.locationService = locationService;
        this.seatHistoryService = seatHistoryService;
        this.eventService = eventService;
        this.sequenceService = sequenceService;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Long createBooking(BookingCrudDto bookingCrudDto, HeaderCollections header) throws BusinessException {
        locationService.findLocationById(bookingCrudDto.getBookingDetailCrudDto().getSeatId())
                .orElseThrow(() -> new BusinessException("BOK_BOOKING_LOCATIONNOTFOUND"));

        String sequence = sequenceService.generateSequenceNo("BOOKING_SEQUENCE", header);

        bookingCrudDto.setBookingNo(sequence);
        EventDto event = eventService.findEventById(bookingCrudDto.getEventId()).orElseThrow(() -> new BusinessException("BOK_BOOKINGS_EVENTNOTFOUND"));
        if(event.getPeriodEnd().compareTo(LocalDateTime.now()) < 0)
            throw new BusinessException("BOK_BOOKINGS_EVENTEXPIRED");
        
        SeatHistoryDto seatHistory = seatHistoryService
                .findSeatHistoryByLocationId(bookingCrudDto.getBookingDetailCrudDto().getSeatId()).orElseThrow(() -> new BusinessException("BOK_BOOKINGS_SEATNOTFOUND"));
        if (!seatHistory.getStatus().equals(SeatHistoryStatus.UNOCCUPIED))
            throw new BusinessException("BOK_BOOKING_SEATISUNAVAILABLE");

        SeatHistoryCrudDto seatHistoryCrudDto = new SeatHistoryCrudDto()
                .setCode(seatHistory.getCode())
                .setLocationId(event.getLocationId())
                .setStatus(SeatHistoryStatus.OCCUPIED);
        seatHistoryService.processReserveSeat(seatHistoryCrudDto, header);

        return bookingRepository.create(bookingCrudDto.toRecord(bookingCrudDto, header));
    }
}
