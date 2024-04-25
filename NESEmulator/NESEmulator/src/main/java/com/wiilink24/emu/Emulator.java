package com.wiilink24.emu;
import javax.swing.SwingUtilities;
import com.wiilink24.emu.ui.SplashScreen;
import com.wiilink24.emu.Menus.MainMenu;
import com.wiilink24.emu.ui.UI;


public class Emulator {

    public static UI ui;

    public static NES nes;


    public static void main(String[] args) throws InterruptedException {
        nes = new NES();
        ui = new UI(nes);
        MainMenu myMenu = new MainMenu();
        SwingUtilities.invokeLater(() -> {
            myMenu.hideMenu();
        });

        SwingUtilities.invokeLater(SplashScreen::new);
        myMenu.showMenu();
        ui.run();
        nes.run();
        ui.hideUI();
    }

    public static UI getUI() {
        return ui;
    }

    public static NES getNES() {
        return nes;
    }
}