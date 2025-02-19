package br.com.nlw.events.repo;

import br.com.nlw.events.model.EventModel;
import br.com.nlw.events.model.SubscriptionModel;
import br.com.nlw.events.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<SubscriptionModel, Integer> {
     public SubscriptionModel findByEventAndSubscriber(EventModel evt, UserModel subscriber);
}
