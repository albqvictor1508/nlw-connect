package br.com.nlw.events.controller;

import br.com.nlw.events.model.SubscriptionModel;
import br.com.nlw.events.model.UserModel;
import br.com.nlw.events.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {
    @Autowired
    private SubscriptionService service;

    @PostMapping("/subscription/{prettyName}")
    public ResponseEntity<SubscriptionModel> subscribe(@PathVariable String prettyName, @RequestBody UserModel subscriber) {
        SubscriptionModel res = service.createSubscription(prettyName, subscriber);

        if(res == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(res);
    }
}
