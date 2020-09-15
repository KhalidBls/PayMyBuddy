package com.paymybuddy.exchange.models;

import java.sql.Timestamp;

public class UserRelationship {

    private int id;
    private int idUserRelating;
    private int idUserRelated;
    private Timestamp timestampOfCreation;

    public UserRelationship(){}

    public UserRelationship(int idUserRelating, int idUserRelated, Timestamp timestampOfCreation) {
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

    public Timestamp getTimestampOfCreation() {
        return timestampOfCreation;
    }

    public void setTimestampOfCreation(Timestamp timestampOfCreation) {
        this.timestampOfCreation = timestampOfCreation;
    }
}
