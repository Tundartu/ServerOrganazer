package com.example.eventorganazer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // Получить все события
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Получить события по дате
    @GetMapping("/{date}")
    public List<Event> getEventsByDate(@PathVariable String date) {
        return eventRepository.findByDateOrderByTimeAsc(date);
    }

    // Создать событие
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        System.out.println("Received Event: " + event.toString());
        return eventRepository.save(event);
    }


    // Обновить событие
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        event.setName(eventDetails.getName());
        event.setDate(eventDetails.getDate());
        event.setTime(eventDetails.getTime());
        event.setDescription(eventDetails.getDescription());
        event.setNotification(eventDetails.isNotification());
        event.setEventDate(eventDetails.getEventDate());

        final Event updatedEvent = eventRepository.save(event);
        return ResponseEntity.ok(updatedEvent);
    }

    // Удалить событие
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        Event event = eventRepository.findById(id).orElseThrow(NoSuchElementException::new);

        eventRepository.delete(event);
        return ResponseEntity.noContent().build();
    }
}
