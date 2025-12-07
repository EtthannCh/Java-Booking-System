package com.example.booking_system.event.model;

import java.time.LocalDateTime;
import java.util.List;

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
    private String duration;
    private List<String> startTime;

    public Event toRecord(HeaderCollections header) {
        String[] startTimeArr = new String[]{};
        if(!startTime.isEmpty())
            startTimeArr = startTime.toArray(String[]::new);

        return new Event(null, name, header.getLocationId(), header.getLocationName(), description, periodEnd,
                periodEnd, duration,startTimeArr ,header.getUserId(), header.getUserName(), null,
                header.getUserId(), header.getUserName(), null);
    }
}
