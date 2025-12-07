package com.example.booking_system.event;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.example.booking_system.event.model.EventCrudDto;
import com.example.booking_system.event.model.EventDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.location.LocationService;
import com.example.booking_system.location.location.model.LocationDto;
import com.example.booking_system.utils.CheckUtil;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final LocationService locationService;

    private Map<String, String> duplicateKeyMap = new HashMap<String, String>(Map.ofEntries(
            Map.entry("events_name_key", "BOK_EVENT_NAMEEXIST")));

    public EventServiceImpl(
            EventRepository eventRepository,
            LocationService locationService
            ) {
        this.eventRepository = eventRepository;
        this.locationService = locationService;
    }

    @Override
    public Optional<EventDto> findEventById(Long eventId) {
        return eventRepository.findEventById(eventId);
    }

    @Override
    public Long createEvent(EventCrudDto eventCrudDto, HeaderCollections header) throws BusinessException {
        try {
            if (eventCrudDto.getPeriodStart().compareTo(eventCrudDto.getPeriodEnd()) >= 0)
                throw new BusinessException("BOK_EVENT_EVENTSTARTGREATERTHANEND");

            if (eventCrudDto.getPeriodStart().compareTo(LocalDateTime.now()) < 0 ||
                    eventCrudDto.getPeriodStart().compareTo(LocalDateTime.now()) < 0)
                throw new BusinessException("BOK_EVENT_PERIODSTARTORENDLESSTHANTODAY");
        
            LocationDto location = locationService.findLocationById(header.getLocationId()).orElseThrow(() -> new BusinessException("BOK_EVENT_LOCATIONNOTFOUND"));
            if(!location.isActive())
                throw new BusinessException("BOK_EVENT_LOCATIONINACTIVE");

            String exampleTimeFormat = "99:99";
            Pattern pattern = Pattern.compile("^\\d{2}:\\d{2}$");
            Matcher matcher = pattern.matcher(exampleTimeFormat);
            if(!matcher.matches())
                throw new BusinessException("BOK_EVENT_STARTTIMEFORMATNOTVALID");

            header.setLocationName(location.getName());
            return eventRepository.createEvent(eventCrudDto.toRecord(header));
        } catch (DuplicateKeyException e) {
            CheckUtil.throwUniqueException(e, duplicateKeyMap);
            throw e;
        }
    }

    public List<EventDto> findListEvent(Long categoryId){
        return eventRepository.findEventList(categoryId);
    }
}
