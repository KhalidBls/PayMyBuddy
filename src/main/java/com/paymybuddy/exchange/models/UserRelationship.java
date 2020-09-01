package com.paymybuddy.exchange.models;

public class UserRelationship {

    private int id;
    private int idUserRelating;
    private int idUserRelated;
    private long timestampOfCreation;

    public UserRelationship(){}

    public UserRelationship(int idUserRelating, int idUserRelated, long timestampOfCreation) {
        this.idUserRelating = idUserRelating;
        this.idUserRelated = idUserRelated;
        this.timestampOfCreation = timestampOfCreation;
    }

    public UserRelationship(int idUserRelating, int idUserRelated) {
        this.idUserRelating = idUserRelating;
        this.idUserRelated = idUserRelated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUserRelating() {
        return idUserRelating;
    }

    public void setIdUserRelating(int idUserRelating) {
        this.idUserRelating = idUserRelating;
    }

    public int getIdUserRelated() {
        return idUserRelated;
    }

    public void setIdUserRelated(int idUserRelated) {
        this.idUserRelated = idUserRelated;
    }

    public long getTimestampOfCreation() {
        return timestampOfCreation;
    }

    public void setTimestampOfCreation(long timestampOfCreation) {
        this.timestampOfCreation = timestampOfCreation;
    }
}
