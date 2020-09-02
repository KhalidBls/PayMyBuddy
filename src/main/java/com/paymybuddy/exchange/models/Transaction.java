package com.paymybuddy.exchange.models;


import com.paymybuddy.exchange.constants.TransactionType;

public class Transaction {

    private int id;
    private double amount;
    private int idUserSender;
    private int idUserReceiver;
    private double fees;
    private int idDescription;
    private TransactionType type;

    public Transaction(){}

    public Transaction(int id, double amount, int idUserSender, int idUserReceiver, double fees, int idDescription, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.idUserSender = idUserSender;
        this.idUserReceiver = idUserReceiver;
        this.fees = fees;
        this.idDescription = idDescription;
        this.type = type;
    }

    public Transaction(double amount, int idUserSender, int idUserReceiver, double fees, int idDescription, TransactionType type) {
        this.amount = amount;
        this.idUserSender = idUserSender;
        this.idUserReceiver = idUserReceiver;
        this.fees = fees;
        this.idDescription = idDescription;
        this.type = type;
    }

    public Transaction(double amount, int idUserSender, int idUserReceiver, int idDescription, TransactionType type) {
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

    public TransactionType getType() {
        return type;
    }

    public void setType(String type) {
        if(type.toLowerCase().equals("payment"))
            this.type = TransactionType.PAYMENT;
        else
            this.type = TransactionType.REFUND;
    }
}
