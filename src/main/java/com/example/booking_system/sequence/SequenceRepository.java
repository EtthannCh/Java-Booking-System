package com.example.booking_system.sequence;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.sequence.model.Sequence;

@Repository
public class SequenceRepository {
    private final JdbcClient jdbcClient;

    public SequenceRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Long create(Sequence sequence) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("""
                insert into sequence
                (
                    name, format, current_number, start_no, reset_condition,
                    created_by_id, created_by, created_at,
                    last_updated_by_id, last_updated_by, last_updated_at
                )
                values
                (
                    :name, :format, :currentNumber, :startNo, :resetCondition,
                    :createdById, :createdBy, now(),
                    :lastUpdatedById, :lastUpdatedBy, now()
                )
                    """)
                .param("name", sequence.name())
                .param("format", sequence.format())
                .param("currentNumber", sequence.current_number())
                .param("startNo", sequence.start_no())
                .param("resetCondition", sequence.reset_condition())
                .param("createdById", sequence.created_by_id())
                .param("createdBy", sequence.created_by())
                .param("lastUpdatedById", sequence.last_updated_by_id())
                .param("lastUpdatedBy", sequence.last_updated_by())
                .update(keyHolder, "id");
        var id = keyHolder.getKey();
        return id.longValue();
    }
}
