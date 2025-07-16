package com.example.booking_system.location;

import org.springframework.stereotype.Service;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.model.LocationCrudDto;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Long createLocation(LocationCrudDto locCrud, HeaderCollections header) {
        return locationRepository.create(locCrud.toRecord(locCrud, header));
    }

}
