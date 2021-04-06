package gui;

import model.Date;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a panel with text fields
public class JTextFieldGUI extends JPanel implements ActionListener {

    private JTextField textAmount; //amount
    private JTextField textType; //type
    private JTextField textSource; //source
    private JTextField textDestination; //destination
    private JTextField textMonth;
    private JTextField textDay;
    private JTextField textYear;

    private int storeAmount;
    private String storeType;
    private String storeSource;
    private String storeDestination;
    private int storeMonth;
    private int storeDay;
    private int storeYear;

    //EFFECTS: Creates text field with text fields initialized
    public JTextFieldGUI() {
        super();
        initTextFields();
        textLayout();
        addActionListener();
        addTextFieldsToPanel();
        setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: initializes text fields
    private void initTextFields() {
        // create text field
        textAmount = new JTextField();
        textAmount.setText("Enter transaction amount here");
        textAmount.setColumns(40);

        textType = new JTextField();
        textType.setText("Enter transaction type here, Inflow or Outflow");
        textType.setColumns(40);

        textSource = new JTextField();
        textSource.setText("Enter transaction source here");
        textSource.setColumns(20);

        textDestination = new JTextField();
        textDestination.setText("Enter transaction destination here");
        textDestination.setColumns(20);

        textMonth = new JTextField();
        textMonth.setText("Enter transaction month here. i.e. 6 is June");
        textMonth.setColumns(20);

        textDay = new JTextField();
        textDay.setText("Enter transaction day here.");
        textDay.setColumns(20);

        textYear = new JTextField();
        textYear.setText("Enter transaction year here.");
        textYear.setColumns(20);
    }


    //MODIFIES: this
    //EFFECTS: sets layout for panel
    private void textLayout() {
        BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(box);
    }

    //MODIFIES: this
    //EFFECTS: adds action listeners to each text field
    private void addActionListener() {
        textAmount.addActionListener(this);
        textType.addActionListener(this);
        textSource.addActionListener(this);
        textDestination.addActionListener(this);
        textMonth.addActionListener(this);
        textDay.addActionListener(this);
        textYear.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: add text fields to panel
    private void addTextFieldsToPanel() {
        add(textAmount);
        add(textType);
        add(textSource);
        add(textDestination);
        add(textMonth);
        add(textDay);
        add(textYear);
    }

    //MODIFIES: this
    //EFFECTS: creates label showing data has been entered and saves data entered when "enter" is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        JLabel label = new JLabel();
        label.setText("Data has been entered");
        label.setSize(50, 50);
        add(label);
        label.setVisible(true);

        storeData();
        clearTexts();
        System.out.println("Data has been added");
    }


    //EFFECTS: clear all texts
    private void clearTexts() {
        textAmount.setText("");
        textType.setText("");
        textSource.setText("");
        textDestination.setText("");
        textDay.setText("");
        textYear.setText("");
        textMonth.setText("");
    }

    //REQUIRES: valid data format
    //MODIFIES: this
    //EFFECTS: saves data entered
    private void storeData() {
        storeAmount = Integer.parseInt(textAmount.getText());
        storeType = textType.getText();
        storeSource = textSource.getText();
        storeDestination = textDestination.getText();
        storeMonth = Integer.parseInt(textMonth.getText());
        storeDay = Integer.parseInt(textDay.getText());
        storeYear = Integer.parseInt(textYear.getText());
    }

    //getters
    public int getStoreAmount() {
        return storeAmount;
    }

    public String getStoreType() {
        return storeType;
    }

    public String getStoreSource() {
        return storeSource;
    }

    public String getStoreDestination() {
        return storeDestination;
    }

    public int getStoreMonth() {
        return storeMonth;
    }

    public int getStoreDay() {
        return storeDay;
    }

    public int getStoreYear() {
        return storeYear;
    }


}
