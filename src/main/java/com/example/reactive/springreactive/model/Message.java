package com.example.reactive.springreactive.model;

public class Message {

    final private String action;
    final private String data;
    final private long id;

    public Message(String action, String data, long id) {
        this.action = action;
        this.data = data;
        this.id = id;
    }
    
    public Message(String action, long id) {
        this.action = action;
        this.data = null;
        this.id = id;
    }

    public String getAction() {
        return action;
    }
    
    public String getData() {
        return data;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Message{" + "action='" + action + '\'' + " data=" + data +", id=" + id + '}';
    }
}

