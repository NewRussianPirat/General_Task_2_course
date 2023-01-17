package UIs;

import Expression.Expression;
import FileReaders.*;
import FileWriters.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class GUI {

    private static GUI gui = null;

    private static final Expression calculator = new Expression();
    private static FileReaders fileReaders;
    private static FileWriters fileWritersTXT = new FileWritersTXT();
    private static final FileWriters fileWritersXML = new FileWritersXML();
    private static final FileWriters fileWritersJSON = new FileWritersJSON();
    private static final FileWriters fileWritersZIP = new FileWritersZIP();

    private static final JFrame mainWindow = new JFrame();

    private static final GUI_Panel mainPanel = new GUI_Panel();

    private static final JLabel helloLabel = new JLabel();
    private static final JLabel whatDoLabel = new JLabel();
    private static final JLabel enterLabel = new JLabel();
    private static final JLabel resultLabel = new JLabel();
    private static final JLabel errorLabel = new JLabel();
    private static final JLabel chooseFileLabel = new JLabel();

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

    private static final JButton equalsButton = new JButton(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            calculationEvent();
        }
    });
    private static final JButton TXTButton = new JButton(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseTXTEvent();
        }
    });
    private static final JButton XMLButton = new JButton(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseXMLEvent();
        }
    });
    private static final JButton JSONButton = new JButton(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseJSONEvent();
        }
    });


    private static final JCheckBox fileWriterCheckBox = new JCheckBox();
    static {
        fileWriterCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showFileWritersEvent();
            }
            else {
                hideFileWritersEvent();
            }
        });
    }
    private static final JCheckBox overwriteFileCheckBox = new JCheckBox();
    static {
        overwriteFileCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                fileWritersTXT.setOverwrite(true);
                fileWritersXML.setOverwrite(true);
                fileWritersJSON.setOverwrite(true);
                fileWritersZIP.setOverwrite(true);
            }
            else {
                fileWritersTXT.setOverwrite(false);
                fileWritersXML.setOverwrite(false);
                fileWritersJSON.setOverwrite(false);
                fileWritersZIP.setOverwrite(false);

            }
        });
    }


    private static final JFileChooser TXTFileChooser = new JFileChooser("outputFiles/");

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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setDefaultWindowSettings();
            setVisibleTrue(
                    helloLabel,
                    whatDoLabel,
                    enterLabel,
                    resultLabel,
                    errorLabel,
                    chooseFileLabel,
                    enterField,
                    equalsButton,
                    fileWriterCheckBox,
                    overwriteFileCheckBox,
                    mainPanel
            );
            hideFileWritersEvent();
            add(
                    helloLabel,
                    whatDoLabel,
                    enterLabel,
                    resultLabel,
                    errorLabel,
                    chooseFileLabel,
                    enterField,
                    equalsButton,
                    TXTButton,
                    XMLButton,
                    JSONButton,
                    fileWriterCheckBox,
                    overwriteFileCheckBox,
                    mainPanel
            );
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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

    @SafeVarargs
    private static <T extends JComponent> void setVisibleFalse(T ... args) {
        for (var jComponent : args) {
            jComponent.setVisible(false);
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
            setResultLabelSettings();
            setChooseFileLabelSettings();
            setEnterFieldSettings();
            setEqualsButtonSettings();
            setTXTButtonSettings();
            setXMLButtonSettings();
            setJSONButtonSettings();
            setFileWriterCheckBoxSettings();
            setOverWriteCheckBoxSettings();
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
            enterLabel.setBounds(this.getWidth() / 24, this.getHeight() / 4, 250, 50);
            enterLabel.setFont(new Font("Arial", Font.BOLD, 20));
            enterLabel.setForeground(new Color(68, 68, 68));
        }

        private void setResultLabelSettings() {
            resultLabel.setHorizontalAlignment(SwingConstants.LEFT);
            resultLabel.setVerticalAlignment(SwingConstants.CENTER);
            resultLabel.setBounds(this.getWidth() / 24 + 315, this.getHeight() / 3, 100, 30);
            resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
            resultLabel.setForeground(new Color(68, 68, 68));
        }

        private void setErrorLabelSettings() {
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            errorLabel.setVerticalAlignment(SwingConstants.CENTER);
            errorLabel.setBounds(this.getWidth() / 24, (int) (this.getHeight() / 2.5), 250, 80);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 18));
            errorLabel.setForeground(new Color(68, 68, 68));
        }

        private void setChooseFileLabelSettings() {
            chooseFileLabel.setText("Choose file with expressions");
            chooseFileLabel.setHorizontalAlignment(SwingConstants.CENTER);
            chooseFileLabel.setVerticalAlignment(SwingConstants.CENTER);
            chooseFileLabel.setBounds(this.getWidth() - 400, this.getHeight() / 4, 300, 50);
            chooseFileLabel.setFont(new Font("Arial", Font.BOLD, 20));
            chooseFileLabel.setForeground(new Color(68, 68, 68));
        }

        private void setEnterFieldSettings() {
            enterField.setBounds(this.getWidth() / 24, this.getHeight() / 3 , 250, 30);
        }

        private void setEqualsButtonSettings() {
            equalsButton.setText("=");
            equalsButton.setHorizontalAlignment(SwingConstants.CENTER);
            equalsButton.setVerticalAlignment(SwingConstants.CENTER);
            equalsButton.setBounds(this.getWidth() / 24 + 260, this.getHeight() / 3, 50, 30);
            equalsButton.setFont(new Font("Arial", Font.BOLD, 20));
            equalsButton.setForeground(new Color(68, 68, 68));
            equalsButton.setFocusable(false);
        }

        private void setTXTButtonSettings() {
            TXTButton.setText("TXT");
            TXTButton.setHorizontalAlignment(SwingConstants.CENTER);
            TXTButton.setVerticalAlignment(SwingConstants.CENTER);
            TXTButton.setBounds(this.getWidth() / 30, fileWriterCheckBox.getY() + 40, 70, 30);
            TXTButton.setFont(new Font("Arial", Font.BOLD, 10));
            TXTButton.setForeground(new Color(68, 68, 68));
            TXTButton.setFocusable(false);
        }
        private void setXMLButtonSettings() {
            XMLButton.setText("XML");
            XMLButton.setHorizontalAlignment(SwingConstants.CENTER);
            XMLButton.setVerticalAlignment(SwingConstants.CENTER);
            XMLButton.setBounds(TXTButton.getX() + 75, fileWriterCheckBox.getY() + 40, 70, 30);
            XMLButton.setFont(new Font("Arial", Font.BOLD, 10));
            XMLButton.setForeground(new Color(68, 68, 68));
            XMLButton.setFocusable(false);
        }
        private void setJSONButtonSettings() {
            JSONButton.setText("JSON");
            JSONButton.setHorizontalAlignment(SwingConstants.CENTER);
            JSONButton.setVerticalAlignment(SwingConstants.CENTER);
            JSONButton.setBounds(XMLButton.getX() + 75, fileWriterCheckBox.getY() + 40, 70, 30);
            JSONButton.setFont(new Font("Arial", Font.BOLD, 10));
            JSONButton.setForeground(new Color(68, 68, 68));
            JSONButton.setFocusable(false);
        }

        private void setFileWriterCheckBoxSettings() {
            fileWriterCheckBox.setText("Write results in a file");
            fileWriterCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
            fileWriterCheckBox.setVerticalAlignment(SwingConstants.CENTER);
            fileWriterCheckBox.setBounds(this.getWidth() / 24, (int)(this.getHeight() / 1.8), 200, 30);
            fileWriterCheckBox.setFont(new Font("Arial", Font.BOLD, 13));
            fileWriterCheckBox.setForeground(new Color(68, 68, 68));
            fileWriterCheckBox.setFocusable(false);
        }

        private void setOverWriteCheckBoxSettings() {
            overwriteFileCheckBox.setText("Overwrite file if it already exists");
            overwriteFileCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
            overwriteFileCheckBox.setVerticalAlignment(SwingConstants.CENTER);
            overwriteFileCheckBox.setBounds(this.getWidth() / 24, (int)(this.getHeight() / 1.4), 250, 30);
            overwriteFileCheckBox.setFont(new Font("Arial", Font.BOLD, 13));
            overwriteFileCheckBox.setForeground(new Color(68, 68, 68));
            overwriteFileCheckBox.setFocusable(false);
        }
    }

    private static void calculationEvent() {
        try {
            String expression = enterField.getText();
            resultLabel.setText(String.format("%.5f", calculator.calculate(expression)));
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

    private static void showFileWritersEvent() {
        setVisibleTrue(
                overwriteFileCheckBox,
                TXTButton,
                XMLButton,
                JSONButton
        );
    }

    private static void hideFileWritersEvent() {
        setVisibleFalse(
                overwriteFileCheckBox,
                TXTButton,
                XMLButton,
                JSONButton
        );
    }

    private static class WrongFormatDialog {

        private final JDialog wrongFormat = new JDialog(mainWindow, "Wrong format", JDialog.DEFAULT_MODALITY_TYPE);
        private final String format;
        private final JLabel wrongLabel = new JLabel();

        WrongFormatDialog(String format1) { format = format1; }

        private void createWrongFormatDialog() {
           setWrongFormatSettings();
           setWrongLabelSettings();
           wrongFormat.add(wrongLabel);
           wrongFormat.setVisible(true);
        }

        private void setWrongFormatSettings() {
            wrongFormat.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            wrongFormat.setBounds(SCREEN_SIZE.width / 2 - 150, SCREEN_SIZE.height / 2 - 75, 300, 150);
            wrongFormat.setLayout(null);
            wrongFormat.setResizable(false);
        }

        private void setWrongLabelSettings() {
            wrongLabel.setText("<html><p align=center>" +
                    "Wrong format chosen<br>Format should be " +
                    format + "</p></html>"
            );
            wrongLabel.setBounds(20, 20, 240, 90);
            wrongLabel.setHorizontalAlignment(SwingConstants.CENTER);
            wrongLabel.setVerticalAlignment(SwingConstants.CENTER);
            wrongLabel.setFont(new Font("Arial", Font.BOLD, 20));
            wrongLabel.setVisible(true);
        }
    }

    private static void chooseTXTEvent() {
        TXTFileChooser.setVisible(true);
        int option = TXTFileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            if (!TXTFileChooser.getSelectedFile().getName().substring(
                    TXTFileChooser.getSelectedFile().getName().lastIndexOf('.'))
                    .equals(".txt")
            ) {
                WrongFormatDialog wrongFormatDialog = new WrongFormatDialog(".txt");
                wrongFormatDialog.createWrongFormatDialog();
            }
            try {
                fileWritersTXT = new FileWritersTXT(TXTFileChooser.getSelectedFile().getPath());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void chooseXMLEvent() {

    }

    private static void chooseJSONEvent() {

    }
}
