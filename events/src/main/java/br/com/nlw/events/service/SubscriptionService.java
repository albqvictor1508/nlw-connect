package br.com.nlw.events.service;

import br.com.nlw.events.dto.SubscriptionResponse;
import br.com.nlw.events.exception.SubscriptionConflictException;
import br.com.nlw.events.exception.EventNotFoundException;
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

    public SubscriptionResponse createSubscription(String eventName, UserModel user) {
        EventModel evt = eventRepo.findByPrettyName(eventName);

        if(evt == null) {
            throw new EventNotFoundException("Event " + eventName + " not found");
        }

        UserModel userRec = userRepo.findByEmail(user.getEmail());

        if(userRec == null) {
            userRec = userRepo.save(user);
        }

        SubscriptionModel subs = new SubscriptionModel();
        subs.setEvent(evt);
        subs.setSubscriber(userRec);

        SubscriptionModel tmpSub = subRepo.findByEventAndSubscriber(evt, userRec);

        if(tmpSub != null) {
            throw new SubscriptionConflictException("Subscription already exists");
        }

        SubscriptionModel res = subRepo.save(subs);

        return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/"+res.getEvent().getPrettyName()+"/"+res.getSubscriber().getId());
    }
}
