package br.com.nlw.events.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_subscription")
public class SubscriptionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_number")
    private Integer subscriptionNumber;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventModel event;

    @ManyToOne
    @JoinColumn(name = "subscribed_user_id")
    private UserModel subscriber;

    @ManyToOne
    @JoinColumn(name = "indication_user_id", nullable = true) //posso entrar sem ser indicado
    private UserModel indication;

    public Integer getSubscriptionNumber() {
        return subscriptionNumber;
    }

    public void setSubscriptionNumber(Integer subscriptionNumber) {
        this.subscriptionNumber = subscriptionNumber;
    }

    public EventModel getEvent() {
        return event;
    }

    public void setEvent(EventModel event) {
        this.event = event;
    }

    public UserModel getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(UserModel subscriber) {
        this.subscriber = subscriber;
    }

    public UserModel getIndication() {
        return indication;
    }

    public void setIndication(UserModel indication) {
        this.indication = indication;
    }
}
