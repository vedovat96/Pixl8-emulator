package com.wiilink24.emu.ui;

import com.wiilink24.emu.Menus.MainMenu;
import com.wiilink24.emu.Emulator;
import com.wiilink24.emu.Menus.SettingsMenu;
import com.wiilink24.emu.PreferencesInstance;
import com.wiilink24.emu.mappers.AbstractMapper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;


public class UIEventListener implements ActionListener, WindowListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int num = 0;
        switch (e.getActionCommand()) {
            case "Games":
                openROM();
                if (num ==0){
                    num +=1;
                }
                else {
                    Emulator.ui.run();
                }
                Emulator.ui.showUI();
                break;
            case "Pause":
                Emulator.getNES().pause();
                break;
            case "Main Menu":
                MainMenu myMenu = new MainMenu();
                myMenu.showMenu();
                Emulator.ui.hideUI();
                break;
            case "Stack":
                viewStack();
                break;
            case "Code":
                viewCode();
                break;
            case "Memory":
                new MemoryChart();
                break;
            case "Settings":
                SwingUtilities.invokeLater(() -> {
                    SettingsMenu settings = new SettingsMenu();
                    settings.showSettings();
                });
                break;
            case "Exit":
                System.exit(0);
                break;
            /*case "1200x1050":
                System.out.println("1200x1050");
                break;
            case "800x700":
                System.out.println("800x700");
                break;
            case "600x525":
                System.out.println("600x525");
                break;
            case "400x350":
                System.out.println("400x350");
                break;*/
        }
    }

    public void openROM() {
        FileDialog fileDialog = new FileDialog(Emulator.getUI());
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setTitle("Select a ROM to load");
        //should open last folder used, and if that doesn't exist, the folder it's running in
        final String path = PreferencesInstance.get().get("filePath", System.getProperty("user.dir", ""));
        final File startDirectory = new File(path);
        if (startDirectory.isDirectory()) {
            fileDialog.setDirectory(path);
        }

        fileDialog.setVisible(true);
        if (fileDialog.getFile() != null) {
           String fullPath = fileDialog.getDirectory() + fileDialog.getFile();
           Emulator.getUI().setCurrentROMName(fullPath);
           Emulator.getNES().loadROM(fullPath);
        }
    }

    public void viewStack() {
        JDialog dialog = new JDialog(Emulator.getUI(), "Stack", true);

        // Create a DefaultListModel and add items to it
        DefaultListModel<Integer> listModel = new DefaultListModel<>();
        int[] stack = Emulator.getNES().getCPU().getStack();
        for (int i = 0; i < 5; i++) {
            listModel.addElement(stack[i]);
        }

        // Create a JList with the DefaultListModel
        JList<Integer> itemList = new JList<>(listModel);

        // Create a JScrollPane to allow scrolling if there are many items
        JScrollPane scrollPane = new JScrollPane(itemList);

        // Add the JScrollPane to the dialog
        dialog.add(scrollPane);

        // Set size and make it visible
        dialog.setSize(200, 150);
        dialog.setVisible(true);
    }

    public void viewCode() {
        JDialog dialog = new JDialog(Emulator.getUI(), "Code", true);

        // Create a DefaultListModel and add items to it
        DefaultListModel<String> listModel = new DefaultListModel<>();
        String[] stack = Emulator.getNES().getCPU().getCode();
        for (int i = 0; i < AbstractMapper.ROM_HIGH - AbstractMapper.ROM_LOW; i++) {
            listModel.addElement(stack[i]);
        }

        // Create a JList with the DefaultListModel
        JList<String> itemList = new JList<>(listModel);

        // Create a JScrollPane to allow scrolling if there are many items
        JScrollPane scrollPane = new JScrollPane(itemList);

        itemList.ensureIndexIsVisible(Emulator.getNES().getCPU().PC - AbstractMapper.ROM_LOW);
        // Add the JScrollPane to the dialog
        dialog.add(scrollPane);

        // Set size and make it visible
        dialog.setSize(400, 500);
        dialog.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
