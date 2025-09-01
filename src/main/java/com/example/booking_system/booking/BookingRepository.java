package com.example.booking_system.booking;

import java.sql.Types;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.booking.model.Booking;
import com.example.booking_system.booking.model.BookingEnum.BookingStatus;

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
                    user_id, event_id, status, booking_no, qty,
                    created_at, created_by, created_by_id,
                    last_updated_at, last_updated_by, last_updated_by_id
                )
                values
                (
                    :userId, :eventId, :status, :bookingNo, :qty,
                    now(), :createdBy, :createdById,
                    now(), :lastUpdatedBy, :lastUpdatedById
                )
                """)
                .param("userId", booking.user_id())
                .param("eventId", booking.event_id())
                .param("status", BookingStatus.DRAFT, Types.VARCHAR)
                .param("bookingNo", booking.booking_no())
                .param("qty", booking.qty())
                .param("createdBy", booking.created_by())
                .param("createdById", booking.created_by_id())
                .param("lastUpdatedBy", booking.last_updated_by())
                .param("lastUpdatedById", booking.last_updated_by_id())
                .update(keyHolder, "id");
        var id = keyHolder.getKey();
        return id.longValue();
    }
}
