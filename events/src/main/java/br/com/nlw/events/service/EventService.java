package br.com.nlw.events.service;

import br.com.nlw.events.repo.EventRepository;
import br.com.nlw.events.model.EventModel;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired //busca os parâmetros colocados no Generic do CrudRepository
    // e cria um objeto com os métodos da interface
    private EventRepository eventRepository; //objeto que segue a interface do CrudRepository

    public EventModel addNewEvent(EventModel event) {
        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "-"));
        return eventRepository.save(event);
    }

    public List<EventModel> getAllEvents() {
        return (List<EventModel>)eventRepository.findAll();
    }

    public EventModel getByPreetyName(String prettyName) {
        return eventRepository.findByPrettyName(prettyName);
    }
}

