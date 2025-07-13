package com.example.booking_system.location;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.location.model.Location;

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
                    name, room_type, created_by_id, created_by, created_at,
                    last_updated_by_id, last_updated_by, last_updated_at
                )
                values
                (
                    :name, :roomType, :createdById, :createdBy, now(),
                    :lastUpdatedById, :lastUpdatedBy, now()
                )
                """)
                .param("name", location.name())
                .param("roomType", location.roomType())
                .param("createdById", location.created_by_id())
                .param("createdBy", location.created_by())
                .param("lastUpdatedById", location.last_updated_by_id())
                .param("lastUpdatedBy", location.last_updated_by())
                .update(keyholder, "id");
        var id = keyholder.getKey();
        return id.longValue();
    }
}
