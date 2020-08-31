package com.paymybuddy.exchange.models;

public class Description {

    private int id;
    private String content;

    public Description(String content) {
        this.id = id;
        this.content = content;
    }

    public Description() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
