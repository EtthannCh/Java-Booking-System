package com.example.booking_system.auth.role;

import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.booking_system.auth.role.model.RoleDto;

@Repository
public class RoleRepository {

    private final JdbcClient jdbcClient;

    public RoleRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<RoleDto> findById(Long id) {
        return jdbcClient.sql("""
                select *
                from roles
                where id = :id
                """)
                .param("id", id)
                .query(RoleDto.class)
                .optional();
    }
}
