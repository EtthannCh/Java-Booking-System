package com.example.booking_system.location;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.model.location.LocationCrudDto;
import com.example.booking_system.location.model.location.LocationDto;
import com.example.booking_system.location.model.location.LocationEnum.RoomType;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Long createLocation(LocationCrudDto locCrud, HeaderCollections header) throws Exception {
        if (locCrud.getRoomType().equals(RoomType.FLOOR)) {
            locationRepository.checkPartOfIsSuitable(locCrud.getPartOf(), RoomType.BUILDING)
                    .orElseThrow(() -> new BusinessException("BOK_LOCATION_PARTOFNOTBUILDING"));
        } else if (locCrud.getRoomType().equals(RoomType.ROOM)) {
            locationRepository.checkPartOfIsSuitable(locCrud.getPartOf(), RoomType.FLOOR)
                    .orElseThrow(() -> new BusinessException("BOK_LOCATION_PARTOFNOTFLOOR"));
        }

        return locationRepository.create(locCrud.toRecord(locCrud, header));
    }

    @Override
    public Optional<LocationDto> checkPartOfIsSuitable(Long id, RoomType roomType) {
        return locationRepository.checkPartOfIsSuitable(id, roomType);
    }

    @Override
    public Optional<LocationDto> findLocationById(Long id) {
        return locationRepository.findLocationById(id);
    }

}
