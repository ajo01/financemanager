package model;


import org.json.JSONArray;
import org.json.JSONObject;

//Represents a transaction with type, amount, source, destination, and date
public class Transaction extends ListOfTransaction {

    protected String type; // inflow or outflow
    protected int amount; // number of cents i.e. 100 = 1 dollar
    protected String whereFrom; // source of transaction
    protected String whereTo; // destination of transaction

    protected Date date; // date of transaction with month day year


    //REQUIRES: amount >= 0, whereFrom and whereTo has non-zero length, valid time
    //EFFECTS: amount, source, destination, and time of transaction is set to the inputs
    public Transaction(int amount, String whereFrom, String whereTo, Date date) {

        this.amount = amount;
        this.whereFrom = whereFrom;
        this.whereTo = whereTo;
        this.date = date;
    }

    //EFFECTS: returns type of transaction
    public String getType() {
        return type;
    }

    //REQUIRES: amount is non negative
    //EFFECTS: returns amount of transaction
    public int getAmount() {
        return amount;
    }

    //EFFECTS: returns from where transaction was sourced
    public String getWhereFrom() {
        return whereFrom;
    }

    //EFFECTS: returns destination of transaction
    public String getWhereTo() {
        return whereTo;
    }

    //EFFECTS: returns date of when transaction occurred
    public Date getDate() {
        return date;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Amount", amount);
        json.put("Source", whereFrom);
        json.put("Destination", whereTo);
        json.put("Date", date.toJson());
        json.put("Type", type);
        return json;
    }

}
