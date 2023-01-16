package UIs;

import Expression.Expression;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class GUI {

    private static GUI gui = null;

    private static final JFrame mainWindow = new JFrame();

    private static final GUI_Panel mainPanel = new GUI_Panel();

    private static final JLabel helloLabel = new JLabel();
    private static final JLabel whatDoLabel = new JLabel();
    private static final JLabel enterLabel = new JLabel();
    private static final JLabel resultLabel = new JLabel();
    private static final JLabel errorLabel = new JLabel();

    private static final JTextField enterField = new JTextField();
    static {
        enterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculationEvent();
                }
            }
        });
        enterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                errorLabelCleanEvent();
                resultLabelCleanEvent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                errorLabelCleanEvent();
                resultLabelCleanEvent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                errorLabelCleanEvent();
                resultLabelCleanEvent();
            }
        });
    }

    private static final JButton equalsButton = new JButton(new EnterFieldAction());

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private static final int STANDARD_RIGHT_ANGLE_X = SCREEN_SIZE.width / 2 - 600;
    private static final int STANDARD_RIGHT_ANGLE_Y = SCREEN_SIZE.height / 2 - 300;
    private static final int STANDARD_WIDTH = 1200;
    private static final int STANDARD_HEIGHT = 600;

    private static final int MINIMUM_WIDTH  = 1200;
    private static final int MINIMUM_HEIGHT = 600;

    private static final Expression calculator = new Expression();

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
                errorLabel,
                enterField,
                equalsButton,
                mainPanel
        );
        add(
                helloLabel,
                whatDoLabel,
                enterLabel,
                resultLabel,
                errorLabel,
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
            setErrorLabelSettings();
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
            resultLabel.setHorizontalAlignment(SwingConstants.LEFT);
            resultLabel.setVerticalAlignment(SwingConstants.CENTER);
            resultLabel.setBounds(this.getWidth() / 24 + 365, this.getHeight() / 3, 100, 30);
            resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
            resultLabel.setForeground(new Color(68, 68, 68));
        }

        private void setErrorLabelSettings() {
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            errorLabel.setVerticalAlignment(SwingConstants.CENTER);
            errorLabel.setBounds(this.getWidth() / 24, (int) (this.getHeight() / 2.5), 300, 80);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 18));
            errorLabel.setForeground(new Color(68, 68, 68));
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

    private static class EnterFieldAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            calculationEvent();
        }
    }

    private static void calculationEvent() {
        try {
            String expression = enterField.getText();
            resultLabel.setText(Double.toString(calculator.calculate(expression)));
        }
        catch (RuntimeException runtimeException) {
            errorLabel.setText("<html><p align=\"center\">"
                    + runtimeException.getMessage()
                    + "<br>please, try again</p></html>");
        }
    }

    private static void errorLabelCleanEvent() {
        errorLabel.setText("");
    }

    private static void resultLabelCleanEvent() {
        resultLabel.setText("");
    }
}
