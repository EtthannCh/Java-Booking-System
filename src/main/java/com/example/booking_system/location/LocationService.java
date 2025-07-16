package com.example.booking_system.location;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.model.LocationCrudDto;

public interface LocationService {
    
    public Long createLocation(LocationCrudDto locCrud, HeaderCollections header);
}
