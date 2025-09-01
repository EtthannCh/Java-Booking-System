package com.example.booking_system.booking.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.booking_system.booking.model.BookingEnum.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDto {
    private Long id;
    private UUID userId;
    private Long eventId;
    private BookingStatus status;
    private String bookingNo;
    private Double qty;
    private LocalDateTime createdAt;
    private String createdBy;
    private UUID createdById;
    private LocalDateTime lastUpdatedAt;
    private String lastUpdatedBy;
    private UUID lastUpdatedById;

    public BookingDto fromRecord(Booking booking) {
        if (booking == null)
            return null;

        return new BookingDto()
                .setId(booking.id())
                .setUserId(booking.user_id())
                .setEventId(booking.event_id())
                .setStatus(booking.status())
                .setBookingNo(booking.booking_no())
                .setQty(booking.qty())
                .setCreatedAt(booking.created__at())
                .setCreatedBy(booking.created_by())
                .setCreatedById(booking.created_by_id())
                .setLastUpdatedAt(booking.last_updated__at())
                .setLastUpdatedBy(booking.last_updated_by())
                .setLastUpdatedById(booking.last_updated_by_id());
    }
}
