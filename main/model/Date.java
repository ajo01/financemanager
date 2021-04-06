package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//Represents date
public class Date implements Writable {
    private int day;
    private int month;
    private int year;
    String pattern = "MM-dd-yyyy";


    //EFFECTS: creates day with given month, day, year
    public Date(int month, int day, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    //get day, month, year
    //EFFECTS: returns value
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    //MODIFIES: this
    //EFFECT: checks if date is valid
    public boolean validDate() {
        try {
            String str = this.month + "-" + this.day + "-" + this.year;
            DateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);
            sdf.parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    //MODIFIES: this
    //EFFECT: return date as string date
    public String dateToString() {
        String dateString;
        if (validDate()) {
            dateString = this.month + "-" + this.day + "-" + this.year;
        } else {
            dateString = "Invalid date";
        }
        return dateString;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Month", month);
        json.put("Day", day);
        json.put("Year", year);
        return json;
    }

}

