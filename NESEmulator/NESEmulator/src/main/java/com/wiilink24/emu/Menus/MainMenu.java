package com.wiilink24.emu.Menus;

import com.wiilink24.emu.ui.UIEventListener;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public JPanel menuPanel;

    public MainMenu() {
        // Set up the frame
        setSize(800, 700); // Set the size as 800x700 pixels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Remove frame decorations

        // Event listener for UI actions.
        UIEventListener listener = new UIEventListener();
        addWindowListener(listener);

        // Create a panel with GridBagLayout
        menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title configuration
        gbc.gridx = 0; // Align to first column
        gbc.gridy = 0; // Place at the first row
        gbc.anchor = GridBagConstraints.NORTHWEST; // Anchor to top-left
        gbc.insets = new Insets(40, 10, 10, 0); // 40 pixels down from the top, 10 pixels from the left side

        JLabel menuLabel = new JLabel("Pixl8");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 70));
        menuPanel.add(menuLabel, gbc);

        // Calculate the y position for the buttons to center them vertically
        Dimension buttonSize = new Dimension(175, 25);
        int totalButtonsHeight = (buttonSize.height + 5) * 4; // Total height of all buttons including spacing
        int startingY = (700 - totalButtonsHeight) / 2; // Centering in the 700px high frame

        gbc.gridy++; // Increment the gridy to move to the next row
        gbc.insets = new Insets(startingY, 10, 0, 0); // Top padding for vertical centering, left align

        // Create buttons with loop and configure their constraints
        JButton[] buttons = new JButton[]{
                new JButton("Games"),
                new JButton("Settings"),
                new JButton("Debugger"),
                new JButton("Exit")
        };

        // Configure GridBagConstraints for buttons
        gbc.anchor = GridBagConstraints.NORTHWEST; // Align to top-left
        gbc.fill = GridBagConstraints.HORIZONTAL; // Horizontal fill

        for (int i = 0; i < buttons.length; i++) {
            if (i > 0) {
                // After adding the first button, reset the top inset to 5 for spacing between buttons
                gbc.insets.top = 5;
            }

            buttons[i].setPreferredSize(buttonSize);
            buttons[i].addActionListener(listener);
            buttons[i].addActionListener(e -> hideMenu());
            menuPanel.add(buttons[i], gbc); // Add button to the panel
            gbc.gridy++; // Increment the gridy to move to the next row
        }

        // Add the panel to the frame
        getContentPane().add(menuPanel); // Add to the content pane

    }

    public void showMenu() {
        setVisible(true);
    }

    // Method to hide the menu
    public void hideMenu() {
        setVisible(false);
    }
}





