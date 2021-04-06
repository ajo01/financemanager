package gui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//Represents the main menu with a main content panel
public class MenuGUI extends JFrame implements ActionListener {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;

    JMenuBar menuBar;
    JMenu menu;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JPanel contentPane;

    private JMenuItem newMenuItem;
    private JMenuItem loadMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem viewMenuItem;
    private JMenuItem revertMenuItem;

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static final String JSON_STORE = "./data/statement.json";
    private ListOfTransaction account;

    private JTextFieldGUI textField;
    private BalanceGUI balanceGUI;
    private JTableGUI table;
    private ImageIcon iconSun;
    private ImageIcon iconPlanet;
    private JLabel sunIconLabel;
    private PlanetIcon planetFrame;


    //EFFECTS: creates a menu, content panel, and balance panel
    public MenuGUI() {

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        account = new ListOfTransaction();

        setTitle("Finance Tracker");
        initMenuItems();
        createMenuBar();
        createContentPane();
        createBalancePane();
        this.add(contentPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    //MODIFIES: this
    //EFFECTS: initializes menu items
    private void initMenuItems() {
        newMenuItem = new JMenuItem("New");
        loadMenuItem = new JMenuItem("Load Data");
        saveMenuItem = new JMenuItem("Save Data");
        exitMenuItem = new JMenuItem("Exit");
        viewMenuItem = new JMenuItem("View");
        revertMenuItem = new JMenuItem("Revert");

    }


    //MODIFIES: this and menu items and menu bar
    //EFFECTS: creates menu bar
    public void createMenuBar() {
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);

        menu = new JMenu();
        menuBar.add(menu);

        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");

        addListeners();
        addMenuItems();
    }

    //MODIFIES: this
    //EFFECTS: adds listeners to menu items
    private void addListeners() {
        // add action listeners
        newMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        loadMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        viewMenuItem.addActionListener(this);
        revertMenuItem.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: adds menu items to menu
    private void addMenuItems() {
        fileMenu.add(newMenuItem);
        fileMenu.add(exitMenuItem);
        fileMenu.add(viewMenuItem);
        fileMenu.add(revertMenuItem);
        editMenu.add(loadMenuItem);
        editMenu.add(saveMenuItem);

        menu.add(fileMenu);
        menu.add(editMenu);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
    }


    //MODIFIES: this
    //EFFECTS: when menu item is clicked, redirect to specified action
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitMenuItem) {
            System.exit(0);
        }
        if (e.getSource() == viewMenuItem) {
            createOneTable();
        }
        if (e.getSource() == newMenuItem) {
            showTextField();
        }
        if (e.getSource() == saveMenuItem) {
            saveData();
        }
        if (e.getSource() == revertMenuItem) {
            returnOriginalPanel();
        }
        if (e.getSource() == loadMenuItem) {
            loadData();
            balanceGUI.setAccount(account);
        }
    }


    //MODIFIES: this
    //EFFECTS: creates a single table showing statement
    private void createOneTable() {
        if (table == null) {
            table = new JTableGUI(account);
        }
        contentPane.add(table);
        table.setVisible(true);
        validate();
        repaint();
    }

    //MODIFIES: this
    //EFFECTS: removes text field and table if displayed
    private void returnOriginalPanel() {
        if (textField != null) {
            contentPane.remove(textField);
        }
        if (table != null) {
            contentPane.remove(table);
        }
        if (sunIconLabel != null) {
            contentPane.remove(sunIconLabel);
        }
        revalidate();
        repaint();
    }


    //MODIFIES: this
    ////EFFECTS: shows text fields on panel
    private void showTextField() {
        textField = new JTextFieldGUI();
        textField.setVisible(true);
        contentPane.add(textField);
        validate();
        repaint();
        System.out.println("JTextField added");
    }

    //MODIFIES: this
    //EFFECTS: saves statement to file
    private void saveData() {
        try {
            saveTransaction();
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
            savedDataText();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: saves transactions
    private void saveTransaction() {
        int day = textField.getStoreDay();
        int month = textField.getStoreMonth();
        int year = textField.getStoreYear();
        Date date = new Date(month, day, year);

        String type = textField.getStoreType();
        String source = textField.getStoreSource();
        String destination = textField.getStoreDestination();
        int amount = textField.getStoreAmount();
        if (type.equals("Inflow")) {
            Transaction t = new Inflow(amount, source, destination, date);
            account.addTransaction(t);
        } else {
            Transaction t = new Outflow(amount, source, destination, date);
            account.addTransaction(t);
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a text field showing that data is saved
    private void savedDataText() {
        planetFrame = new PlanetIcon(iconPlanet);
        planetFrame.pack();
        validate();
        repaint();
    }


    //MODIFIES: this
    //EFFECTS: creates an icon image when data is saved
    private void sunIconImage() {
        try {
            BufferedImage myPictureSun = ImageIO.read(new File("./data/image/happysun.png"));
            if (iconSun == null) {
                iconSun = new ImageIcon(myPictureSun);
                sunIconLabel = new JLabel(iconSun);
                contentPane.add(sunIconLabel);
                sunIconLabel.setVisible(true);
            }
            System.out.println("Happy sun!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //MODIFIES: this
    //EFFECTS: loads data unto account
    private void loadData() {
        try {
            account = jsonReader.read();
            sunIconImage();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a content pane
    public void createContentPane() {

        JLabel welcome = new JLabel("Welcome to the Finance Tracker!");
        JLabel instructions = new JLabel("Please click new in the menu bar to add transactions.");
        JLabel instructions2 = new JLabel("Press enter then click save data in the menu to save");
        JLabel sun = new JLabel("A sun or planet will appear when you've saved or loaded successfully");

        contentPane = new JPanel();
        BoxLayout box = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
        contentPane.setLayout(box);
        contentPaneSettings(welcome, instructions, instructions2, sun);
    }

    //MODIFIES: this
    //EFFECTS: adds labels to contentpane
    private void contentPaneSettings(JLabel welcome, JLabel instructions, JLabel instructions2, JLabel sun) {
        contentPane.setOpaque(true);
        contentPane.add(welcome);
        contentPane.add(instructions);
        contentPane.add(instructions2);
        contentPane.add(sun);

        contentPane.setBackground(new Color(255, 225, 94));
        add(contentPane, BorderLayout.CENTER);
        contentPane.setVisible(true);
    }


    //MODIFIES: this
    //EFFECTS: adds balance panel to main content panel
    public void createBalancePane() {
        balanceGUI = new BalanceGUI(account);
        balanceGUI.setVisible(true);
        contentPane.add(balanceGUI);
        validate();
        repaint();
        System.out.println("Balance panel added");
    }

}
