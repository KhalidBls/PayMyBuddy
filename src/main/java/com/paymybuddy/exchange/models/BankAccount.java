package com.paymybuddy.exchange.models;

public class BankAccount {

    private int id;
    private String iban;
    private String swift;
    private int idUser;

    public BankAccount(){}

    public BankAccount(String iban, String swift, int idUser) {
        this.iban = iban;
        this.swift = swift;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
