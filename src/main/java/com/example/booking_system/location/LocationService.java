package com.example.booking_system.location;

import java.util.Optional;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.model.location.LocationCrudDto;
import com.example.booking_system.location.model.location.LocationDto;
import com.example.booking_system.location.model.location.LocationEnum.RoomType;

public interface LocationService {

    public Long createLocation(LocationCrudDto locCrud, HeaderCollections header) throws Exception;

    public Optional<LocationDto> findLocationById(Long id);

    public Optional<LocationDto> checkPartOfIsSuitable(Long id, RoomType roomType);
}
