package model;

//Represents the outflow of transactions
public class Outflow extends Transaction {

    // Creates a transaction with type set to Outflow
    public Outflow(int amount, String whereFrom, String whereTo, Date date) {
        super(amount, whereFrom, whereTo, date);
        type = "Outflow";
    }

}
