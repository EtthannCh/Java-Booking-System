package com.example.booking_system.event.model;

import java.time.LocalDateTime;

import com.example.booking_system.header.HeaderCollections;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventCrudDto {
    private String name;
    private String description;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;

    public Event toRecord(HeaderCollections header) {
        return new Event(null, name, description, periodEnd, periodEnd, header.getUserId(), header.getUserName(), null,
                header.getUserId(), header.getUserName(), null);
    }
}
