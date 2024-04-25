import com.wiilink24.emu.Menus.MainMenu;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
            // Run the application
        MainMenu myMenu = new MainMenu();
        SwingUtilities.invokeLater(() -> {
            myMenu.showMenu();
        });
        }
    }
