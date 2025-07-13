package com.example.booking_system.location.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.example.booking_system.location.model.LocationEnum.RoomType;

public record Location(
        @Id Long id,
        String name,
        RoomType roomType,
        Long part_of,
        UUID created_by_id,
        String created_by,
        LocalDateTime created_at,
        UUID last_updated_by_id,
        String last_updated_by,
        LocalDateTime last_updated_at) {
}
