package com.example.booking_system.booking;

import java.sql.Types;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.booking.model.Booking;

@Repository
public class BookingRepository {
    private final JdbcClient jdbcCLient;

    public BookingRepository(JdbcClient jdbcClient) {
        this.jdbcCLient = jdbcClient;
    }

    public Long create(Booking booking) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcCLient.sql("""
                insert into booking
                (
                    room_id, room_name, room_type, 
                    created_by_id, created_by, created_at,
                    last_updated_by_id, last_updated_by, last_updated_at
                )
                values
                (
                    :roomId, :roomName, :roomType,
                    :createdById, :createdBy, now(),
                    :lastUpdatedById, :lastUpdatedBy, now()
                )
                """)
                .param("roomId", booking.room_id())
                .param("roomName", booking.room_name())
                .param("roomType", booking.room_type(), Types.VARCHAR)
                .param("createdBy", booking.created_by())
                .param("createdById", booking.created_by_id())
                .param("lastUpdatedBy", booking.created_by())
                .param("lastUpdatedById", booking.created_by_id())
                .update(keyHolder, "id");
        var id = keyHolder.getKey();
        return id.longValue();
    }
}
