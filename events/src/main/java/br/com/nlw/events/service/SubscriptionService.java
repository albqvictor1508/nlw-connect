package br.com.nlw.events.service;

import br.com.nlw.events.dto.SubscriptionRankingByUser;
import br.com.nlw.events.dto.SubscriptionRankingItem;
import br.com.nlw.events.dto.SubscriptionResponse;
import br.com.nlw.events.exception.SubscriptionConflictException;
import br.com.nlw.events.exception.EventNotFoundException;
import br.com.nlw.events.exception.UserIndicatorNotFoundException;
import br.com.nlw.events.model.EventModel;
import br.com.nlw.events.model.SubscriptionModel;
import br.com.nlw.events.model.UserModel;
import br.com.nlw.events.repo.EventRepository;
import br.com.nlw.events.repo.SubscriptionRepository;
import br.com.nlw.events.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

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

    public SubscriptionResponse createSubscription(String eventName, UserModel user, Integer userId) {
        EventModel evt = eventRepo.findByPrettyName(eventName);

        if(evt == null) {
            throw new EventNotFoundException("Event " + eventName + " not found");
        }

        UserModel userRec = userRepo.findByEmail(user.getEmail());

        if(userRec == null) {
            userRec = userRepo.save(user);
        }

        UserModel indicator = null;
        if(userId != null) {
            indicator = userRepo.findById(userId).orElse(null);

            if(indicator == null) {
                throw new UserIndicatorNotFoundException("user indicate"+userId+" not found");
            }
        }

        SubscriptionModel subs = new SubscriptionModel();
        subs.setEvent(evt);
        subs.setSubscriber(userRec);
        subs.setIndication(indicator);

        SubscriptionModel tmpSub = subRepo.findByEventAndSubscriber(evt, userRec);

        if(tmpSub != null) {
            throw new SubscriptionConflictException("Subscription already exists");
        }

        SubscriptionModel res = subRepo.save(subs);

        return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/"+res.getEvent().getPrettyName()+"/"+res.getSubscriber().getId());
    }

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName) {
        EventModel evt = eventRepo.findByPrettyName(prettyName);

        if(evt == null) {
            throw new EventNotFoundException("Event " + prettyName + " not found");
        }

        return subRepo.generateRanking(evt.getId());
    }

    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer userId) {
       List<SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);

       SubscriptionRankingItem item = ranking.stream().filter(i -> i.userId().equals(userId)).findFirst().orElse(null);
       if(item == null) {
           throw new UserIndicatorNotFoundException("Não há inscrições com indicação do usuário: " + userId);
       }

        Integer position = IntStream.range(0, ranking.size()).filter(pos -> ranking.get(pos).userId().equals(userId)).findFirst().getAsInt();
       return new SubscriptionRankingByUser(item, position + 1);
    }
}
