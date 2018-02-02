package com.example.reactive.springreactive.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reactive.springreactive.di.SpringExtension;
import com.example.reactive.springreactive.model.Message;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

@Service
public class CompletableFutureService {

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;
    
    private AtomicLong actorId = new AtomicLong(0);
    private AtomicLong insertActorId = new AtomicLong(0);

    public CompletableFuture<Message> get(String action, Long id) {
        CompletableFuture<Message> future = new CompletableFuture<>();
        ActorRef workerActor = actorSystem.actorOf(springExtension.props("workerActor", future), "worker-actor");
        workerActor.tell(new Message(action, id), null);
        return future;
    }
    
 
    public CompletableFuture<Message> getDisplay(String action, Long id) {
        CompletableFuture<Message> future = new CompletableFuture<>();
        ActorRef workerActor = actorSystem.actorOf(springExtension.props("displayActor", future), "display-actor" + actorId.getAndIncrement());
        workerActor.tell(new Message(action, id), null);
        return future;
    }
    
    
    public CompletableFuture<Message> getInsert(String action, Long id) {
        CompletableFuture<Message> future = new CompletableFuture<>();
        ActorRef workerActor = actorSystem.actorOf(springExtension.props("insertActor", future), "insert-actor" + insertActorId.getAndIncrement());
        workerActor.tell(new Message(action, id), null);
        return future;
    }
}
