package UIs;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private static GUI gui = null;

    private static final JFrame jFrame = new JFrame("Super Calculator");

    private static final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    private static final int STANDARD_RIGHT_ANGLE_X = dimension.width / 2 - 600;
    private static final int STANDARD_RIGHT_ANGLE_Y = dimension.height / 2 - 300;
    private static final int STANDARD_WIDTH = 1200;
    private static final int STANDARD_HEIGHT = 600;

    private static final int MINIMUM_WIDTH = 1200;
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
    }

    private void setDefaultWindowSettings() {
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setBounds(STANDARD_RIGHT_ANGLE_X, STANDARD_RIGHT_ANGLE_Y, STANDARD_WIDTH, STANDARD_HEIGHT);
        jFrame.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        jFrame.setVisible(true);
    }

}
