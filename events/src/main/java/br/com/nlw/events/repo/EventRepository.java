package br.com.nlw.events.repo;

import br.com.nlw.events.model.EventModel;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<EventModel, Integer> {
    public EventModel findByPrettyName(String prettyName);
}
