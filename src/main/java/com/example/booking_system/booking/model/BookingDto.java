package com.example.booking_system.booking.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.booking_system.location.model.LocationEnum.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDto {
    private Long id;
    private Long roomId;
    private RoomType roomType;
    private String roomName;
    private String bookingNo;
    private LocalDateTime createdAt;
    private String createdBy;
    private UUID createdById;

    public BookingDto fromRecord(Booking booking) {
        if (booking == null)
            return null;

        return new BookingDto()
                .setId(booking.id())
                .setRoomId(booking.room_id())
                .setRoomType(booking.room_type())
                .setBookingNo(booking.booking_no())
                .setCreatedAt(booking.created__at())
                .setCreatedBy(booking.created_by())
                .setCreatedById(booking.created_by_id());
    }
}
