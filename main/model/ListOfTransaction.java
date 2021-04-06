package model;

import exception.InvalidDateInputException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//Creates a List of Transactions
public class ListOfTransaction implements Writable {

    private static final int DEBT_LIMIT = 1000000; // $10 000 max debt

    private List<Transaction> statement;
    private int balance; // balance can be negative
    private int inflowAmount;
    private int outflowAmount;
    private boolean inDebt;
    private int monthlyBalance;


    // Creates an empty list of transaction with balance = 0
    public ListOfTransaction() {
        statement = new ArrayList<>();
        balance = 0;
        inDebt = false;
    }

    // EFFECTS: adds given transaction to the list
    public void addTransaction(Transaction t) {
        statement.add(t);
        if (t.type.equals("Inflow")) {
            balance = balance + t.amount;
        } else {
            balance = balance - t.amount;
        }
    }

    // EFFECTS: Returns the number of transactions in the list
    public int size() {
        return statement.size();
    }

    //EFFECTS: returns true if given transaction is in the list
    public boolean contains(Transaction t) {
        return statement.contains(t);
    }

    //EFFECTS: returns a list of transaction of type inflow only
    public ListOfTransaction inflowOnly() {
        ListOfTransaction inflowList;
        inflowList = new ListOfTransaction();
        for (Transaction t : statement) {
            if (t.type.equals("Inflow")) {
                inflowList.addTransaction(t);
            }
        }
        return inflowList;
    }

    //EFFECTS: returns a list of transaction of type outflow only
    public ListOfTransaction outflowOnly() {
        ListOfTransaction outflowList;
        outflowList = new ListOfTransaction();
        for (Transaction t : statement) {
            if (t.type.equals("Outflow")) {
                outflowList.addTransaction(t);
            }
        }
        return outflowList;
    }

    //EFFECTS: returns total balance
    public int getTotalBalance() {
        return balance;
    }

    //MODIFIES: this
    // EFFECTS: Returns balance at month m, year y  i. e. March: Inflow: +5 Outflow: +6
    public String getBalanceAt(int m, int y) throws InvalidDateInputException {
        checkDateInputs(m, y);
        addMonthlyBalance(m, y);
        return "Inflow: +" + inflowAmount + " Outflow: -" + outflowAmount
                + " Monthly Balance: " + monthlyBalance;
    }


    //MODIFIES: this
    // EFFECTS: Returns balance at month m, year y
    private void addMonthlyBalance(int m, int y) {
        for (Transaction t : statement) {
            if (checkMonthYear(m, y, t, "Inflow")) {
                inflowAmount = inflowAmount + t.amount;
                monthlyBalance = monthlyBalance + t.amount;
            } else if (checkMonthYear(m, y, t, "Outflow")) {
                outflowAmount = outflowAmount + t.amount;
                monthlyBalance = monthlyBalance - t.amount;
            }
        }
    }

    //EFFECTS: returns true if there is a transaction that matches the inputted month year and type
    private boolean checkMonthYear(int m, int y, Transaction t, String str) {
        return t.type.equals(str) && m == t.getDate().getMonth() && y == t.getDate().getYear();
    }

    //EFFECTS: throw exception if month input it not between 1 and 12, or if year input is not in the past 100 years
    private void checkDateInputs(int m, int y) throws InvalidDateInputException {
        if (m > 12 || m < 1 || y > 2020 || y < 1920) {
            throw new InvalidDateInputException();
        }
    }


    //EFFECTS: shows if user is in debt or not
    public boolean isInDebt() {
        if (getTotalBalance() < 0) {
            inDebt = true;
        }
        return inDebt;
    }

    //EFFECTS: shows transactions in list
    public List<Transaction> showTransactions() {
        return statement;
    }

    // EFFECTS: returns an unmodifiable list of transactions
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(statement);
    }


    // EFFECTS: returns the ith transaction
    public Transaction get(int i) {
        return statement.get(i);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("My statement", transactionsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : statement) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}
