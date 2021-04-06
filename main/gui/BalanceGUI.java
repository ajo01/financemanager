package gui;

import model.ListOfTransaction;
import model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents a panel with a balance button
public class BalanceGUI extends JPanel implements ActionListener {
    private JButton balanceButton;
    private JLabel balanceLabel;
    private String operand;
    private ListOfTransaction statement;
    private String totalBalance;

    //EFFECTS: Creates a panel with a button that updates total balance
    public BalanceGUI(ListOfTransaction statement) {
        this.statement = statement;
        init();
        render();
    }


    //MODIFIES: this
    //EFFECTS: initializes button
    private void init() {
        balanceButton = new JButton("See Total Balance");
        balanceButton.setActionCommand("balanceButton");
        balanceButton.addActionListener(this);

        balanceLabel = new JLabel(Integer.toString(0));
    }

    //MODIFIES: this
    //EFFECTS: renders button
    private void render() {
        balanceButton.setOpaque(true);
        balanceButton.setBackground(new Color(255, 172, 99));
        balanceButton.setBorderPainted(false);
        add(balanceButton);
        add(balanceLabel);
        balanceButton.setVisible(true);
        balanceLabel.setVisible(true);
        setOpaque(true);
        setSize(new Dimension(200, 200));
        setBackground(new Color(255, 225, 94));
    }

    //MODIFIES: this and balance
    //EFFECTS: if balance button is pressed, update balance
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("balanceButton")) {
            updateBalance();
        }
    }

    //MODIFIES: this
    //EFFECTS: update balance
    private void updateBalance() {
        int balance = 0;
        for (Transaction t: statement.getTransactions()) {
            if (t.getType().equals("Inflow")) {
                balance = balance + t.getAmount();
            } else {
                balance -= t.getAmount();
            }
        }
        if (balance < 0) {
            operand = "-";
        } else {
            operand = "";
        }
        balanceLabel.setText(operand + balance);
        validate();
        repaint();
    }

    //EFFECTS: set statement to account
    public void setAccount(ListOfTransaction account) {
        this.statement = account;
    }
}
