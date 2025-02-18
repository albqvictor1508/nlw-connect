package br.com.nlw.events.controller;

import br.com.nlw.events.model.EventModel;
import br.com.nlw.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    @Autowired
    private EventService service;

    public EventController() {}

    @PostMapping("/events")
    public EventModel addNewEvent(@RequestBody EventModel newEvent) {
        return service.addNewEvent(newEvent);
    }

    @GetMapping("/events")
    public List<EventModel> getAllEvents() {
        return service.getAllEvents();
    }

    @GetMapping("/events/{prettyName}")
    public ResponseEntity<EventModel> getEventByPreetyName(@PathVariable String prettyName) {
        EventModel evt = service.getByPreetyName(prettyName);
        if (evt == null) return ResponseEntity.notFound().build();

        return ResponseEntity.status(200).body(evt);
    }
}
