package com.example.reactive.springreactive.actor;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.reactive.springreactive.model.Message;
import com.example.reactive.springreactive.service.BusinessService;

import akka.actor.UntypedActor;

@Component("workerActor")
@Scope("prototype")
public class WorkerActor extends UntypedActor {

    @Autowired
    private BusinessService businessService;

    final private CompletableFuture<Message> future;

    public WorkerActor(CompletableFuture<Message> future) {
        this.future = future;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        businessService.perform(this + " " + message);

        if (message instanceof Message) {
            future.complete((Message) message);
        } else {
            unhandled(message);
        }

        getContext().stop(self());
    }
}
