package br.com.nlw.events.service;

import br.com.nlw.events.model.EventModel;
import br.com.nlw.events.model.SubscriptionModel;
import br.com.nlw.events.model.UserModel;
import br.com.nlw.events.repo.EventRepository;
import br.com.nlw.events.repo.SubscriptionRepository;
import br.com.nlw.events.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private EventRepository eventRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SubscriptionRepository subRepo;

    public SubscriptionModel createSubscription(String eventName, UserModel user) {
        SubscriptionModel subs = new SubscriptionModel();
        EventModel evt = eventRepo.findByPrettyName(eventName);
        user = userRepo.save(user);

        subs.setEvent(evt);
        subs.setSubscriber(user);
        SubscriptionModel res = subRepo.save(subs);

        return res;
    }
}
