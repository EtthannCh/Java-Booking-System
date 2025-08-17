package com.example.booking_system.location.location.model;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.location.model.LocationEnum.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationCrudDto {
    private String name;
    private RoomType roomType;
    private Long partOf;
    private String section;
    private String row;
    private Long col;

    public Location toRecord(LocationCrudDto locCrud, HeaderCollections header) {
        if (locCrud == null)
            return null;

        return new Location(null, name, roomType, partOf, section, row, col, header.getUserId(), header.getUserName(),
                null,
                header.getUserId(), header.getUserName(), null);
    }
}
