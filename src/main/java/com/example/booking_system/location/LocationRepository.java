package com.example.booking_system.location;

import java.sql.Types;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.location.model.location.Location;
import com.example.booking_system.location.model.location.LocationDto;
import com.example.booking_system.location.model.location.LocationEnum.RoomType;
import com.example.booking_system.location.model.seat_history.SeatHistory;

@Repository
public class LocationRepository {
    private final JdbcClient jdbcClient;

    public LocationRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Long create(Location location) {
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcClient.sql("""
                insert into location
                (
                    name, room_type, part_of ,created_by_id, created_by, created_at,
                    last_updated_by_id, last_updated_by, last_updated_at
                )
                values
                (
                    :name, :roomType, :partOf ,:createdById, :createdBy, now(),
                    :lastUpdatedById, :lastUpdatedBy, now()
                )
                """)
                .param("name", location.name().toUpperCase())
                .param("roomType", location.roomType(), Types.VARCHAR)
                .param("partOf", location.part_of())
                .param("createdById", location.created_by_id())
                .param("createdBy", location.created_by())
                .param("lastUpdatedById", location.last_updated_by_id())
                .param("lastUpdatedBy", location.last_updated_by())
                .update(keyholder, "id");
        var id = keyholder.getKey();
        return id.longValue();
    }

    public Optional<LocationDto> findLocationById(Long id) {
        return jdbcClient.sql("""
                select *
                from location
                where id = :id
                """)
                .param("id", id)
                .query(LocationDto.class)
                .optional();
    }

    public Optional<LocationDto> checkPartOfIsSuitable(Long id, RoomType roomType) {
        return jdbcClient.sql("""
                select *
                from location
                where id = :id and room_type = :roomType
                """)
                .param("id", id)
                .param("roomType", roomType, Types.VARCHAR)
                .query(LocationDto.class)
                .optional();
    }

    public String createSeatHistory(SeatHistory seatHistory) {
        return jdbcClient.sql("""
                insert into seat_history
                (
                    code,status,created_by_id,created_by,created_at,
                    last_updated_by_id,last_updated_by,last_updated_at
                )
                values
                (
                    :code, :status, :createdById, :createdBy, now(),
                     :lastUpdatedById, :lastUpdatedBy, now()
                )
                returning code;
                """)
                .param("code", seatHistory.code())
                .param("status", seatHistory.status(), Types.VARCHAR)
                .param("createdById", seatHistory.created_by_id())
                .param("createdBy", seatHistory.created_by())
                .param("lastUpdatedById", seatHistory.last_updated_by_id())
                .param("lastUpdatedBy", seatHistory.last_updated_by())
                .query(String.class).single();
    }
}
