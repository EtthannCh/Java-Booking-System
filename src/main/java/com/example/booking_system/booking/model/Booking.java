package com.example.booking_system.booking.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.example.booking_system.location.model.LocationEnum.RoomType;

public record Booking(
                @Id Long id,
                Long room_id,
                String room_name,
                RoomType room_type,
                String booking_no,
                LocalDateTime created__at,
                String created_by,
                UUID created_by_id) {

}
