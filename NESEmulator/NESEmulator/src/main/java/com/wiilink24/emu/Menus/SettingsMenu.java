package com.wiilink24.emu.Menus;

import com.wiilink24.emu.ui.UIEventListener;

import javax.swing.*;
import java.awt.*;

public class SettingsMenu extends JFrame {
    public SettingsMenu() {
        // Setting the title of the window and default close operation
        UIEventListener listener = new UIEventListener();
        this.addWindowListener(listener);
        this.setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700); // Set the window size

        // Create the tabbed pane with centered tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14)); // Set font for tab titles
        UIManager.put("TabbedPane.contentAreaColor", Color.lightGray); // Set background color for the tab content area

        // Video Settings Tab
        JPanel videoPanel = new JPanel();
        videoPanel.setLayout(new BoxLayout(videoPanel, BoxLayout.Y_AXIS));
        videoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] rexText = {"1200x1050","800x700", "600x525", "400x350"};
        JComboBox<String> rezBox= new JComboBox<>(rexText) ;
        videoPanel.add(createSettingComponent("Resolution:",rezBox));
        rezBox.addActionListener(listener);
        videoPanel.add(createSettingComponent("Fullscreen Mode:", new JCheckBox("Enable Fullscreen")));
        videoPanel.add(createSettingComponent("V-Sync:", new JCheckBox("Enable V-Sync")));
        JScrollPane videoScrollPane = new JScrollPane(videoPanel);
        videoScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tabbedPane.addTab("Video", videoScrollPane);

        // Audio Settings Tab
        JPanel audioPanel = new JPanel();
        audioPanel.setLayout(new BoxLayout(audioPanel, BoxLayout.Y_AXIS));
        audioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        audioPanel.add(createSettingComponent("Master Volume:", new JSlider()));
        audioPanel.add(createSettingComponent("Mute All Sounds:", new JCheckBox("Mute")));
        audioPanel.add(createSettingComponent("Background Music Volume:", new JSlider()));
        JScrollPane audioScrollPane = new JScrollPane(audioPanel);
        audioScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tabbedPane.addTab("Audio", audioScrollPane);

        // Controls Settings Tab
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        controlsPanel.add(createSettingComponent("Key Bindings:", new JButton("Configure Key Bindings")));
        controlsPanel.add(createSettingComponent("Gamepad Configuration:", new JButton("Configure Gamepad")));
        JScrollPane controlsScrollPane = new JScrollPane(controlsPanel);
        controlsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tabbedPane.addTab("Controls", controlsScrollPane);

        // Add tabbed pane to the frame
        getContentPane().add(tabbedPane);
    }

    // Helper method to create a panel for each setting with a label and a component
    private JPanel createSettingComponent(String labelText, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(200, 20)); // Uniform label size for alignment
        panel.add(label);
        panel.add(component);
        return panel;
    }
    public void showSettings(){
        setVisible(true);
    }
    public void hideSettings(){
        setVisible(false);
    }

}


