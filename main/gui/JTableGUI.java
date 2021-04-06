package gui;

import model.ListOfTransaction;
import model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

//Represents a panel with a table to display transactions
public class JTableGUI extends JPanel {
    private ListOfTransaction statement;
    private JTable table;

    private final String[] columnNames = {
            "Amount", "Type", "Source", "Destination", "Date"
    };
    private Object[][] tableData;


    //EFFECTS: Creates a table that can load data into it
    public JTableGUI(ListOfTransaction statement) {
        this.statement = statement;
        setPreferredSize(new Dimension(1000, 200));
        tableData = new Object[statement.size()][5];
        BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(box);
        loadDataTable();
        initTable();
    }


    //MODIFIES: this
    //EFFECTS: initializes table
    public void initTable() {

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);

        table = new JTable(tableModel);
        table.setVisible(true);
        table.setBackground(Color.pink);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(table.getTableHeader());
        scrollPane.setViewportView(table);
        add(scrollPane);
    }


    //MODIFIES: this
    //EFFECTS: loads data unto table
    public void loadDataTable() {
        for (int i = 0; i < statement.size(); i++) {
            Object[] sub = new Object[5];
            Transaction t = statement.get(i);
            sub[0] = String.valueOf(t.getAmount());
            sub[1] = t.getType();
            sub[2] = t.getWhereFrom();
            sub[3] = t.getWhereTo();
            sub[4] = t.getDate().dateToString();
            tableData[i] = sub;
        }
    }
}

