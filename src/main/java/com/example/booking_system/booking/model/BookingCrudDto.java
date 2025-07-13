package com.example.booking_system.booking.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.model.LocationEnum.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingCrudDto {
    private Long roomId;
    private String roomName;
    private RoomType roomType;
    private String bookingNo;
    private UUID createdById;
    private String createdBy;
    private LocalDateTime createdAt;

    public Booking toRecord(BookingCrudDto bCrudDto, HeaderCollections header) {
        if (bCrudDto == null)
            return null;

        return new Booking(null, roomId, roomName, roomType, bookingNo, null, header.getUserName(), header.getUserId());
    }
}
