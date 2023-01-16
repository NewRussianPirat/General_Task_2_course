package UIs;

import javax.swing.*;
import java.awt.*;
public class GUI {

    private static GUI gui = null;

    private static final JFrame mainWindow = new JFrame();
    private static final GUI_Panel mainPanel = new GUI_Panel();
    private static final JLabel helloLabel = new JLabel(
            "Welcome to my Super Calculator!",
            SwingConstants.CENTER
    );
    private static final JLabel whatDoLabel = new JLabel(
            "Please, choose how you want to enter expression",
            SwingConstants.CENTER
    );

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private static final int STANDARD_RIGHT_ANGLE_X = SCREEN_SIZE.width / 2 - 600;
    private static final int STANDARD_RIGHT_ANGLE_Y = SCREEN_SIZE.height / 2 - 300;
    private static final int STANDARD_WIDTH = 1200;
    private static final int STANDARD_HEIGHT = 600;

    private static final int MINIMUM_WIDTH  = 1200;
    private static final int MINIMUM_HEIGHT = 600;

    public static GUI getInstance() {
        if (gui == null) {
            gui = new GUI();
        }
        return gui;
    }

    private GUI() {}

    public void createGUI() {
        setDefaultWindowSettings();
        setVisibleTrue(
                helloLabel,
                whatDoLabel,
                mainPanel
        );
        add(
                helloLabel,
                whatDoLabel,
                mainPanel
        );
    }

    private void setDefaultWindowSettings() {
        mainWindow.setTitle("Super Calculator");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setBounds(STANDARD_RIGHT_ANGLE_X, STANDARD_RIGHT_ANGLE_Y, STANDARD_WIDTH, STANDARD_HEIGHT);
        mainWindow.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        mainWindow.setVisible(true);
    }

    @SafeVarargs
    private static <T extends JComponent> void add(T ... args) {
        for (var jComponent : args) {
            mainWindow.add(jComponent);
        }
    }

    @SafeVarargs
    private static <T extends JComponent> void setVisibleTrue(T ... args) {
        for (var jComponent : args) {
            jComponent.setVisible(true);
        }
    }

    private static class GUI_Panel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
//            Graphics2D graphics2D = (Graphics2D) g;
            this.setBounds(0, 0, mainWindow.getWidth(), mainWindow.getHeight());

            this.setBackground(new Color(232, 232, 232));

            helloLabel.setBounds(this.getWidth() / 2 - 160, 25 , 320, 50);
            helloLabel.setFont(new Font("Arial", Font.BOLD, 20));
            helloLabel.setForeground(new Color(68, 68, 68));

            whatDoLabel.setBounds(this.getWidth() / 2 - 300, 75 , 600, 50);
            whatDoLabel.setFont(new Font("Arial", Font.BOLD, 20));
            whatDoLabel.setForeground(new Color(68, 68, 68));

            this.setLayout(null);
        }
    }
}
