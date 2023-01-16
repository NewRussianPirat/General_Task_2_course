package UIs;

import javax.swing.*;
import java.awt.*;
public class GUI {

    private static GUI gui = null;

    private static final JFrame mainWindow = new JFrame();

    private static final GUI_Panel mainPanel = new GUI_Panel();

    private static final JLabel helloLabel = new JLabel();
    private static final JLabel whatDoLabel = new JLabel();
    private static final JLabel enterLabel = new JLabel();
    private static final JLabel resultLabel = new JLabel();

    private static final JTextField enterField = new JTextField();

    private static final JButton equalsButton = new JButton();

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
                enterLabel,
                resultLabel,
                enterField,
                equalsButton,
                mainPanel
        );
        add(
                helloLabel,
                whatDoLabel,
                enterLabel,
                resultLabel,
                enterField,
                equalsButton,
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
            this.setLayout(null);

            setHelloLabelSettings();
            setWhatDoLabelSettings();
            setEnterLabelSettings();
            setEnterFieldSettings();
            setEqualsButtonSettings();
            setResultLabelSettings();
        }

        private void setHelloLabelSettings() {
            helloLabel.setText("Welcome to my Super Calculator!");
            helloLabel.setHorizontalAlignment(SwingConstants.CENTER);
            helloLabel.setVerticalAlignment(SwingConstants.CENTER);
            helloLabel.setBounds(this.getWidth() / 2 - 160, this.getHeight() / 25, 320, 50);
            helloLabel.setFont(new Font("Arial", Font.BOLD, 20));
            helloLabel.setForeground(new Color(68, 68, 68));
        }

        private void setWhatDoLabelSettings() {
            whatDoLabel.setText("Please, choose how you want to enter expression");
            whatDoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            whatDoLabel.setVerticalAlignment(SwingConstants.CENTER);
            whatDoLabel.setBounds(this.getWidth() / 2 - 300, this.getHeight() / 8 , 600, 50);
            whatDoLabel.setFont(new Font("Arial", Font.BOLD, 20));
            whatDoLabel.setForeground(new Color(68, 68, 68));
        }

        private void setEnterLabelSettings() {
            enterLabel.setText("Write yourself");
            enterLabel.setHorizontalAlignment(SwingConstants.CENTER);
            enterLabel.setVerticalAlignment(SwingConstants.CENTER);
            enterLabel.setBounds(this.getWidth() / 24, this.getHeight() / 4, 300, 50);
            enterLabel.setFont(new Font("Arial", Font.BOLD, 20));
            enterLabel.setForeground(new Color(68, 68, 68));
        }

        private void setResultLabelSettings() {
            resultLabel.setText("result");
            resultLabel.setHorizontalAlignment(SwingConstants.LEFT);
            resultLabel.setVerticalAlignment(SwingConstants.CENTER);
            resultLabel.setBounds(this.getWidth() / 24 + 365, this.getHeight() / 3, 100, 30);
            equalsButton.setFont(new Font("Arial", Font.BOLD, 20));
            equalsButton.setForeground(new Color(68, 68, 68));
        }

        private void setEnterFieldSettings() {
            enterField.setBounds(this.getWidth() / 24, this.getHeight() / 3 , 300, 30);
        }

        private void setEqualsButtonSettings() {
            equalsButton.setText("=");
            equalsButton.setHorizontalAlignment(SwingConstants.CENTER);
            equalsButton.setVerticalAlignment(SwingConstants.CENTER);
            equalsButton.setBounds(this.getWidth() / 24 + 310, this.getHeight() / 3, 50, 30);
            equalsButton.setFont(new Font("Arial", Font.BOLD, 20));
            equalsButton.setForeground(new Color(68, 68, 68));
        }
    }
}
