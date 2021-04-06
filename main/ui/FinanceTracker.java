package ui;


import exception.InvalidDateInputException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//based on TellerApp console

//Finance Tracker Application
public class FinanceTracker {

    private ListOfTransaction account;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/statement.json";

    public FinanceTracker() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        account = new ListOfTransaction();
        runTracking();
    }

    public void runTracking() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");

    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("d")) {
            deposit();
        } else if (command.equals("w")) {
            withdraw();
        } else if (command.equals("b")) {
            printBalance(account);
        } else if (command.equals("i")) {
            printInflowOnly();
        } else if (command.equals("o")) {
            printOutflowOnly();
        } else if (command.equals("m")) {
            printMonthlyStatement();
        } else if (command.equals("s")) {
            saveStatement();
        } else if (command.equals("l")) {
            loadStatement();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes account
    private void init() {
        account = new ListOfTransaction();
        input = new Scanner(System.in);
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome! Please select from:");
        System.out.println("\td -> deposit");
        System.out.println("\tw -> withdraw");
        System.out.println("\tb -> See balance");
        System.out.println("\ti -> See savings only");
        System.out.println("\to -> See expenses only");
        System.out.println("\tm -> See account statement by month");
        System.out.println("\ts -> Save data");
        System.out.println("\tl -> Load data");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void deposit() {
        System.out.print("Enter amount to deposit (in cents): ");
        int amount = input.nextInt();
        System.out.print("Source of transaction: ");
        String source = input.next();
        System.out.print("Destination of transaction: ");
        String destination = input.next();
        System.out.print("Enter month: ");
        int month = input.nextInt();
        System.out.print("Enter day: ");
        int day = input.nextInt();
        System.out.print("Enter year: ");
        int year = input.nextInt();


        if (amount >= 0.0) {
            Transaction in = new Inflow(amount, source, destination, new Date(month, day, year));
            account.addTransaction(in);
        } else {
            System.out.println("Cannot deposit negative amount...\n");
        }

        printBalance(account);
    }

    // MODIFIES: this
    // EFFECTS: conducts a withdraw transaction
    private void withdraw() {
        System.out.print("Enter amount to withdraw (in cents): ");
        int amount = input.nextInt();
        System.out.print("Source of transaction: ");
        String source = input.next();
        System.out.print("Destination of transaction: ");
        String destination = input.next();
        System.out.print("Enter month: ");
        int month = input.nextInt();
        System.out.print("Enter day: ");
        int day = input.nextInt();
        System.out.print("Enter year: ");
        int year = input.nextInt();
        Date date = new Date(month, day, year);

        if (amount < 0.0) {
            System.out.println("Cannot withdraw negative amount...\n");
        } else if (account.getTotalBalance() < amount) {
            System.out.println("Insufficient balance on account... You have borrowed money. \n");
        } else {
            Transaction out = new Outflow(amount, source, destination, new Date(month, day, year));
            account.addTransaction(out);
        }

        printBalance(account);
    }

    //EFFECTS: prints out all transactions of type inflow
    private void printInflowOnly() {
        ListOfTransaction inflowList = account.inflowOnly();

        for (Transaction t : inflowList.showTransactions()) {
            System.out.println("Amount: " + t.getAmount());
            System.out.println("Date: " + t.getDate().dateToString());
            System.out.println("From: " + t.getWhereFrom());
            System.out.println("To: " + t.getWhereTo());
        }
    }

    //EFFECTS: prints out all transactions of type outflow
    private void printOutflowOnly() {
        ListOfTransaction outflowList = account.outflowOnly();

        for (Transaction t : outflowList.showTransactions()) {
            System.out.println("Amount: " + t.getAmount());
            System.out.println("Date: " + t.getDate().dateToString());
            System.out.println("From: " + t.getWhereFrom());
            System.out.println("To: " + t.getWhereTo());
        }
    }

    //EFFECTS: prints out all transactions of the given month and year
    private void printMonthlyStatement() {
        System.out.print("Enter which year's statement you would like to see: ");
        int year = input.nextInt();
        System.out.print("Enter which month's statement you would like to see: ");
        int month = input.nextInt();

        String monthlyReport = null;
        try {
            monthlyReport = account.getBalanceAt(month, year);
        } catch (InvalidDateInputException e) {
            System.out.println("Invalid time inputs. Please try again.");
        }
        System.out.println(monthlyReport);
    }

    //EFFECTS: saves statement to file
    public void saveStatement() {
        try {
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
            System.out.println("Saved account to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadStatement() {
        try {
            account = jsonReader.read();
            System.out.println("Loaded account from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints balance of account to the screen
    private void printBalance(ListOfTransaction account) {
        System.out.print("Balance:" + account.getTotalBalance());
    }

}
