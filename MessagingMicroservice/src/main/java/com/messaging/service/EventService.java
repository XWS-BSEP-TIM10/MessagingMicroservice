package com.messaging.service;

import com.messaging.model.Event;

import java.util.List;

public interface EventService {
    Event save(Event event);
    List<Event> findAll();
}
