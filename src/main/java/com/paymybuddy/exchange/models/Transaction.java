package com.paymybuddy.exchange.models;

import javax.persistence.*;

@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="amount")
    private double amount;

    @Column(name="id_user_sender")
    private int idUserSender;

    @Column(name="id_user_receiver")
    private int idUserReceiver;

    @Column(name="fees")
    private double fees;

    @Column(name="id_description")
    private int idDescription;

    @Column(name="type")
    private String type;

    public Transaction(){}

    public Transaction(double amount, int idUserSender, int idUserReceiver, double fees, int idDescription, String type) {
        this.amount = amount;
        this.idUserSender = idUserSender;
        this.idUserReceiver = idUserReceiver;
        this.fees = fees;
        this.idDescription = idDescription;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getIdUserSender() {
        return idUserSender;
    }

    public void setIdUserSender(int idUserSender) {
        this.idUserSender = idUserSender;
    }

    public int getIdUserReceiver() {
        return idUserReceiver;
    }

    public void setIdUserReceiver(int idUserReceiver) {
        this.idUserReceiver = idUserReceiver;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public int getIdDescription() {
        return idDescription;
    }

    public void setIdDescription(int idDescription) {
        this.idDescription = idDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
