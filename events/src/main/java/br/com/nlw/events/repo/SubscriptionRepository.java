package br.com.nlw.events.repo;

import br.com.nlw.events.dto.SubscriptionRankingItem;
import br.com.nlw.events.model.EventModel;
import br.com.nlw.events.model.SubscriptionModel;
import br.com.nlw.events.model.UserModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<SubscriptionModel, Integer> {
     public SubscriptionModel findByEventAndSubscriber(EventModel evt, UserModel subscriber);

     @Query(value = "SELECT COUNT(subscription_number) AS quantity, indication_user_id, user_name "
             + "FROM tbl_subscription INNER JOIN tbl_user "
             + "ON tbl_subscription.indication_user_id = tbl_user.user_id "
             + "WHERE indication_user_id IS NOT NULL "
             + "AND event_id = :eventId "
             + "GROUP BY indication_user_id "
             + "ORDER BY quantity desc", nativeQuery = true)
     public List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);
}
