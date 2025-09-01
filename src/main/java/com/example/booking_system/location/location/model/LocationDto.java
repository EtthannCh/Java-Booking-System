package com.example.booking_system.location.location.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.location.model.LocationEnum.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto {
    private Long id;
    private String name;
    private RoomType roomType;
    private Long partOf;
    private String section;
    private String row;
    private Long col;
    private UUID createdById;
    private String createdBy;
    private LocalDateTime createdAt;
    private UUID lastUpdatedById;
    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedAt;

    public LocationDto fromRecord(Location location, HeaderCollections header) {
        if (location == null)
            return null;

        return new LocationDto()
                .setId(location.id())
                .setName(location.name())
                .setRoomType(location.roomType())
                .setPartOf(location.part_of())
                .setSection(location.section())
                .setRow(location.row())
                .setCol(location.col())
                .setCreatedById(location.created_by_id())
                .setCreatedBy(location.created_by())
                .setCreatedById(location.created_by_id())
                .setLastUpdatedById(location.last_updated_by_id())
                .setLastUpdatedBy(location.last_updated_by())
                .setLastUpdatedAt(location.last_updated_at());
    }

    public List<LocationDto> fromRecordList(List<Location> locList, HeaderCollections header) {
        if (locList.isEmpty())
            return Collections.emptyList();

        List<LocationDto> loc = new ArrayList<>();
        for (Location location : locList) {
            loc.add(fromRecord(location, header));
        }
        return loc;
    }
}
