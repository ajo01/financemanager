package persistence;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import model.*;
import org.json.*;

// Represents a reader that reads account from JSON data stored in file
// from JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfTransaction read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfTransaction(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses lot from JSON object and returns it
    private ListOfTransaction parseListOfTransaction(JSONObject jsonObject) {
        ListOfTransaction lot = new ListOfTransaction();
        addTransactions(lot, jsonObject);
        return lot;
    }


    // MODIFIES: lot
    // EFFECTS: parses transactions from JSON object and adds them to list
    private void addTransactions(ListOfTransaction lot, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("My statement");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(lot, nextTransaction);
        }
    }


    // MODIFIES: t
    // EFFECTS: parses transaction from JSON object and adds it to list
    private void addTransaction(ListOfTransaction lot, JSONObject jsonObject) {
        int amount = jsonObject.getInt("Amount");
        String whereFrom = jsonObject.getString("Source");
        String whereTo = jsonObject.getString("Destination");
        String type = jsonObject.getString("Type");

        JSONObject rawDate = jsonObject.getJSONObject("Date");
        int month = rawDate.getInt("Month");
        int day = rawDate.getInt("Day");
        int year = rawDate.getInt("Year");
        Date date = new Date(month, day, year);
        if (type.equals("Inflow")) {
            Transaction t = new Inflow(amount, whereFrom, whereTo, date);
            lot.addTransaction(t);
        } else {
            Transaction t = new Outflow(amount, whereFrom, whereTo, date);
            lot.addTransaction(t);
        }
    }
}

