package com.example.booking_system.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.example.booking_system.event.model.EventCrudDto;
import com.example.booking_system.event.model.EventDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.utils.CheckUtil;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private Map<String, String> duplicateKeyMap = new HashMap<String, String>(Map.ofEntries(
            Map.entry("events_name_key", "BOK_EVENT_NAMEEXIST")));

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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

            return eventRepository.createEvent(eventCrudDto.toRecord(header));
        } catch (DuplicateKeyException e) {
            CheckUtil.throwUniqueException(e, duplicateKeyMap);
            throw e;
        }
    }

}
