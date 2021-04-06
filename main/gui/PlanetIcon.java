package gui;

import javax.swing.*;
import java.awt.*;

//Represents a frame with a planet gif playing in it
public class PlanetIcon extends JFrame {
    private JLabel marsIconLabel;
    private ImageIcon iconMars;
    private JPanel contentPane;
    private JLabel savedLabel;

    //EFFECTS: Creates a panel with a gif playing
    public PlanetIcon(ImageIcon icon) {
        this.iconMars = icon;
        init();
        render();
        frameSet();
    }

    //EFFECTS: Creates a panel for gif with text to be played on
    private void init() {
        Font f = new Font("serif", Font.PLAIN, 30);
        savedLabel = new JLabel("Your data has been saved!");
        savedLabel.setVisible(true);
        savedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        savedLabel.setFont(f);

        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(savedLabel, BorderLayout.PAGE_END);
    }

    //MODIFIES: this
    //EFFECTS: Creates settings for frame
    private void frameSet() {
        setTitle("Saving Data...");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: renders gif onto panel
    private void render() {
        if (iconMars == null) {
            iconMars = new ImageIcon("./data/image/cheeseplanet.gif");
            marsIconLabel = new JLabel(iconMars);
            contentPane.add(marsIconLabel, BorderLayout.CENTER);
            contentPane.setVisible(true);
        }
        System.out.println("Planet in orbit");
    }
}
