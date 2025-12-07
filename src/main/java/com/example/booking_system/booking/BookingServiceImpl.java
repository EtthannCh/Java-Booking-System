package com.example.booking_system.booking;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.booking_system.auth.role.model.RoleEnum.RoleCodeEnum;
import com.example.booking_system.auth.user_account.UserAccountService;
import com.example.booking_system.auth.user_account.model.UserAccountDto;
import com.example.booking_system.booking.model.BookingCrudDto;
import com.example.booking_system.booking.model.BookingDetailCrudDto;
import com.example.booking_system.event.EventService;
import com.example.booking_system.event.model.EventDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.location.LocationService;
import com.example.booking_system.location.location.model.LocationDto;
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
        private final BookingDetailService bookingDetailService;
        private final UserAccountService userAccountService;

        public BookingServiceImpl(
                        BookingRepository bookingRepository,
                        LocationService locationService,
                        SeatHistoryService seatHistoryService,
                        EventService eventService,
                        SequenceService sequenceService,
                        BookingDetailService bookingDetailService,
                        UserAccountService userAccountService) {
                this.bookingRepository = bookingRepository;
                this.locationService = locationService;
                this.seatHistoryService = seatHistoryService;
                this.eventService = eventService;
                this.sequenceService = sequenceService;
                this.bookingDetailService = bookingDetailService;
                this.userAccountService = userAccountService;
        }

        @Override
        @Transactional(rollbackFor = BusinessException.class)
        public Long createBooking(BookingCrudDto bookingCrudDto, HeaderCollections header) throws BusinessException {
                UserAccountDto user = userAccountService.findByUserId(header.getUserId())
                                .orElseThrow(() -> new BusinessException("BOK_BOOKING_USERNOTFOUND"));
                if (!user.getRoleCode().equals(RoleCodeEnum.USER))
                        throw new BusinessException("BOK_BOOKING_USERNOTALLOWED");

                locationService.findLocationById(bookingCrudDto.getBookingDetailCrudDto().getSeatId())
                                .orElseThrow(() -> new BusinessException(
                                                "BOK_BOOKING_LOCATIONNOTFOUND"));

                EventDto event = eventService.findEventById(bookingCrudDto.getEventId())
                                .orElseThrow(() -> new BusinessException("BOK_BOOKINGS_EVENTNOTFOUND"));

                Double totalBooking = bookingRepository.findBookingCountForSingleEventPerPeriod(
                                bookingCrudDto.getEventId(),
                                bookingCrudDto.getShowTime());
                if (totalBooking > 100)
                        throw new BusinessException("BOK_BOOKINGS_NOAVAILABLESEATS");

                String sequence = sequenceService.generateSequenceNo("BOOKING_SEQUENCE", header);

                bookingCrudDto.setBookingNo(sequence);
                if (event.getPeriodEnd() != null && event.getPeriodEnd().compareTo(LocalDateTime.now()) < 0)
                        throw new BusinessException("BOK_BOOKINGS_EVENTEXPIRED");

                Optional<SeatHistoryDto> seatHistory = seatHistoryService
                                .findSeatHistoryByLocationId(bookingCrudDto.getBookingDetailCrudDto().getSeatId());
                if (seatHistory.isPresent() && !seatHistory.get().getStatus().equals(SeatHistoryStatus.UNOCCUPIED))
                        throw new BusinessException("BOK_BOOKING_SEATISUNAVAILABLE");

                LocationDto location = locationService.findLocationById(event.getLocationId())
                                .orElseThrow(() -> new BusinessException("BOK_BOOKING_LOCATIONNOTFOUND"));

                SeatHistoryCrudDto seatHistoryCrudDto = new SeatHistoryCrudDto()
                                .setCode(String.join("/", location.getSection(), location.getRow(),
                                                location.getCol().toString()))
                                .setLocationId(event.getLocationId())
                                .setStatus(SeatHistoryStatus.OCCUPIED);
                seatHistoryService.processReserveSeat(seatHistoryCrudDto, header);

                Long bookingId = bookingRepository.create(bookingCrudDto.toRecord(bookingCrudDto, header));
                BookingDetailCrudDto bookingDetailCrudDto = new BookingDetailCrudDto()
                                .setBookingId(bookingId)
                                .setSeatId(location.getId())
                                .setPrice(10D);
                bookingDetailService.createBookingDetail(bookingDetailCrudDto, header);

                // create user is mandatory for booking a movie ticket
                return bookingId;
        }
}
