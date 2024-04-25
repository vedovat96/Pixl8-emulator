package com.wiilink24.emu.ui;

import com.wiilink24.emu.NES;
import com.wiilink24.emu.Utilities;
import com.wiilink24.emu.video.RGBRenderer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

// This class represents the main UI component of an NES emulator.
public class UI extends JFrame {

    private Canvas canvas; // The canvas on which NES frames will be rendered.
    private static int WIDTH = 350; // Width of the canvas
    private static int HEIGHT = 400; // Height of the canvas
    private BufferStrategy buffer; // Used for managing buffered rendering.
    private final long[] frametimes = new long[60]; // Stores frame render times to calculate FPS.
    private int frametimeptr = 0; // Pointer/index for frametimes array.
    private final UIEventListener listener = new UIEventListener(); // Event listener for UI actions.
    private com.wiilink24.emu.video.Renderer renderer = new RGBRenderer(); // Renderer for converting NES output to RGB.
    NES nes; // Reference to the NES emulator core.
    public int num = 0;
    private final GameController padController1, padController2; // Game controllers for player input.
    String currentROMName; // Name of the current ROM loaded into the emulator.

    // Constructor initializes components and controllers.
    public UI(NES nes) throws InterruptedException {
        createMenuBar();
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.nes = nes;
        padController1 = new GameController(this, 0); // Controller for player 1.
        padController2 = new GameController(this, 1); // Controller for player 2.
        nes.setControllers(padController1, padController2);
        padController1.startEventQueue();
        padController2.startEventQueue();
        padController1.setButtons();
        padController2.setButtons();
    }

    // Main execution method to set up and display the UI.
    public synchronized void run() {
        this.setResizable(false);
        createMenuBar(); // Sets up the menu items.
        startRenderer(); // Initializes the rendering system.
        // Register key actions.
        this.getRootPane().registerKeyboardAction(listener, "Escape",
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        this.addWindowListener(listener);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    }
    // show ui on the screen
    public void showUI(){
        this.setVisible(true);
    }
    // hide ui from the screen
    public void hideUI(){
        this.setVisible(false);
    }


    // Creates and configures the menu bar with various options.
    private void createMenuBar() {
        JMenuBar menus = new JMenuBar();

        // File menu for ROM operations.
        JMenu menu = new JMenu("Menu");
        JMenuItem item = new JMenuItem("Main Menu");
        item.addActionListener(listener);
        menu.add(item);
        item = new JMenuItem("Pause");
        item.addActionListener(listener);
        menu.add(item);


        // Create a button that you can access the settings at any time
        JMenu settings = new JMenu("Settings");
        item = new JMenuItem("Settings Menu");
        item.addActionListener(listener);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        settings.add(item);

        // Debugger menu for viewing emulator internals.
        JMenu debugger = new JMenu("Debugger");
        item = new JMenuItem("Stack");
        item.addActionListener(listener);
        debugger.add(item);
        item = new JMenuItem("Code");
        item.addActionListener(listener);
        debugger.add(item);
        item = new JMenuItem("Memory");
        item.addActionListener(listener);
        debugger.add(item);

        // Adding all menus to the menu bar.
        menus.add(menu);
        menus.add(settings);
        menus.add(debugger);
        this.setJMenuBar(menus);
    }

    // Sets up the canvas and renderer for drawing NES output.
    private void startRenderer() {
        if (canvas != null) {
            this.remove(canvas);
        }
        renderer.setClip(8);
        canvas = new Canvas();
        canvas.setSize(WIDTH*2, HEIGHT*2);
        canvas.setEnabled(false);
        this.add(canvas);
        this.pack();
        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();
    }

    // Displays an error message dialog.
    public static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
    }

    int bgcolor;
    BufferedImage frame;
    double fps;
    int frameskip = 0; // Skips drawing some frames to improve performance.

    // Updates the frame to be rendered, adjusting FPS and drawing the frame.
    public final synchronized void setFrame(final int[] nextframe, final int[] bgcolors, boolean dotcrawl) {
        frametimes[frametimeptr] = nes.getFrameTime();
        ++frametimeptr;
        frametimeptr %= frametimes.length;

        if (frametimeptr == 0) {
            long averageframes = 0;
            for (long l : frametimes) {
                averageframes += l;
            }
            averageframes /= frametimes.length;
            fps = 1E9 / averageframes;
            this.setTitle(String.format("NES Emulator %s - %s, %2.2f fps"
                            + ((frameskip > 0) ? " frameskip " + frameskip : ""),
                    0.1,
                    currentROMName,
                    fps));
        }
        if (nes.framecount % (frameskip + 1) == 0) {
            frame = renderer.render(nextframe, bgcolors, dotcrawl);
            render();
        }
    }

    // Renders the current frame to the canvas.
    public synchronized void render() {
        Graphics graphics = buffer.getDrawGraphics();
        graphics.drawImage(frame, 0, 0, WIDTH*2, HEIGHT*2, null);

        graphics.dispose();
        buffer.show();
    }

    // Updates the currently loaded ROM name.
    public void setCurrentROMName(String path) {
        currentROMName = Utilities.getFileName(path);
    }

    public void setNum(int num) {
        this.num = num;
    }
}

