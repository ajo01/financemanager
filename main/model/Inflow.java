package model;

//Represents the inflow of transactions
public class Inflow extends Transaction {

    // Creates a transaction with type set to Inflow
    public Inflow(int amount, String whereFrom, String whereTo, Date date) {
        super(amount, whereFrom, whereTo, date);
        type = "Inflow";

    }
}
