package UIs;

import Expression.Expression;
import FileReaders.*;
import FileWriters.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI {

    private static GUI gui = null;

    private static FileWriters fileWriters = new FileWritersTXT();

    private static final JFrame mainWindow = new JFrame();

    private static final GUI_Panel mainPanel = new GUI_Panel();

    private static final JLabel helloLabel = new JLabel();
    private static final JLabel whatDoLabel = new JLabel();
    private static final JLabel enterLabel = new JLabel();
    private static final JLabel resultOwnEnterLabel = new JLabel();
    private static final JLabel errorLabel = new JLabel();
    private static final JLabel chooseFileLabel = new JLabel();
    private static final JLabel chosenWriteFile = new JLabel();
    private static final JLabel resultFileReadLabel = new JLabel();
    static {
        resultFileReadLabel.setVisible(false);
    }

    private static final JTextField enterField = new JTextField();
    static {
        enterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculationOwnEnterEvent();
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

    private static final JButton fileReadButton = new JButton(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            calculationFileReadEvent();
        }
    });
    private static final JButton equalsButton = new JButton(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            calculationOwnEnterEvent();
        }
    });
    private static final JButton TXTButton = new JButton(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseFileWriteEvent(".txt");
        }
    });
    private static final JButton XMLButton = new JButton(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseFileWriteEvent(".xml");
        }
    });
    private static final JButton JSONButton = new JButton(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseFileWriteEvent(".json");
        }
    });
    private static final JButton stopWritingButton = new JButton(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            stopWritingEvent();
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
                fileWriters.setOverwrite(true);
                fileWriters.setOverwrite(true);
                fileWriters.setOverwrite(true);
            }
            else {
                fileWriters.setOverwrite(false);
                fileWriters.setOverwrite(false);
                fileWriters.setOverwrite(false);

            }
        });
    }


    private static final JFileChooser fileWriteChooser = new JFileChooser("outputFiles\\");
    private static final JFileChooser fileReadChooser = new JFileChooser("inputFiles\\");

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
                    resultOwnEnterLabel,
                    errorLabel,
                    resultFileReadLabel,
                    chooseFileLabel,
                    chosenWriteFile,
                    enterField,
                    fileReadButton,
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
                    resultOwnEnterLabel,
                    errorLabel,
                    chooseFileLabel,
                    resultFileReadLabel,
                    chosenWriteFile,
                    enterField,
                    equalsButton,
                    fileReadButton,
                    TXTButton,
                    XMLButton,
                    JSONButton,
                    stopWritingButton,
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
            setFileReadButtonSettings();
            setTXTButtonSettings();
            setXMLButtonSettings();
            setJSONButtonSettings();
            setStopWritingButtonSettings();
            setFileWriterCheckBoxSettings();
            setOverWriteCheckBoxSettings();
            setChosenWriteFileLabelSettings();
            setResultFileWriteLabelSettings();
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
            resultOwnEnterLabel.setHorizontalAlignment(SwingConstants.LEFT);
            resultOwnEnterLabel.setVerticalAlignment(SwingConstants.CENTER);
            resultOwnEnterLabel.setBounds(this.getWidth() / 24 + 315, this.getHeight() / 3, 100, 30);
            resultOwnEnterLabel.setFont(new Font("Arial", Font.BOLD, 20));
            resultOwnEnterLabel.setForeground(new Color(68, 68, 68));
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

        private void setChosenWriteFileLabelSettings() {
            chosenWriteFile.setHorizontalAlignment(SwingConstants.LEFT);
            chosenWriteFile.setVerticalAlignment(SwingConstants.CENTER);
            chosenWriteFile.setBounds(TXTButton.getX(), TXTButton.getY() + 35, 200, 30);
            chosenWriteFile.setFont(new Font("Arial", Font.BOLD, 15));
            chosenWriteFile.setForeground(new Color(68, 68, 68));
        }

        private void setResultFileWriteLabelSettings() {
            resultFileReadLabel.setHorizontalAlignment(SwingConstants.LEFT);
            resultFileReadLabel.setVerticalAlignment(SwingConstants.NORTH);
            resultFileReadLabel.setBounds(fileReadButton.getX(), fileReadButton.getY() + 35, this.getWidth() - fileReadButton.getX() - 20, this.getHeight() - fileReadButton.getY() - 35);
            resultFileReadLabel.setFont(new Font("Arial", Font.BOLD, 15));
            resultFileReadLabel.setForeground(new Color(68, 68, 68));
        }

        private void setEnterFieldSettings() {
            enterField.setBounds(this.getWidth() / 24, this.getHeight() / 3 , 250, 30);
        }

        private void setFileReadButtonSettings() {
            fileReadButton.setText("Choose file");
            fileReadButton.setHorizontalAlignment(SwingConstants.CENTER);
            fileReadButton.setVerticalAlignment(SwingConstants.CENTER);
            fileReadButton.setBounds(this.getWidth() - 400, this.getHeight() / 3, 300, 30);
            fileReadButton.setFont(new Font("Arial", Font.BOLD, 20));
            fileReadButton.setForeground(new Color(68, 68, 68));
            fileReadButton.setFocusable(false);
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

        private void setStopWritingButtonSettings() {
            stopWritingButton.setText("Stop writing");
            stopWritingButton.setHorizontalAlignment(SwingConstants.CENTER);
            stopWritingButton.setVerticalAlignment(SwingConstants.CENTER);
            stopWritingButton.setBounds(chosenWriteFile.getX(), chosenWriteFile.getY() + 35, 200, 30);
            stopWritingButton.setFont(new Font("Arial", Font.BOLD, 15));
            stopWritingButton.setForeground(new Color(68, 68, 68));
            stopWritingButton.setFocusable(false);
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
            overwriteFileCheckBox.setBounds(this.getWidth() / 24, (int)(this.getHeight() / 1.2), 250, 30);
            overwriteFileCheckBox.setFont(new Font("Arial", Font.BOLD, 13));
            overwriteFileCheckBox.setForeground(new Color(68, 68, 68));
            overwriteFileCheckBox.setFocusable(false);
        }
    }

    private static void calculationOwnEnterEvent() {
        try {
            String expression = enterField.getText();
            String result = String.format("%.5f", Expression.calculate(expression));
            if (fileWriterCheckBox.isSelected()) {
                if (fileWriters == null || !fileWriters.isActive()) {
                    errorLabel.setText("Please, select file first");
                    return;
                }
                fileWriters.writeFile(result);
                if (fileWriters.getClass() == FileWritersTXT.class) {
                    fileWriters.writeFile("\n");
                }
            }
            resultOwnEnterLabel.setText(result);
        }
        catch (RuntimeException runtimeException) {
            errorLabel.setText("<html><p align=\"center\">"
                    + runtimeException.getMessage()
                    + "<br>please, try again</p></html>");
        }
    }

    private static void calculationFileReadEvent() {
        try {
            if (fileWriterCheckBox.isSelected()) {
                if (fileWriters == null || !fileWriters.isActive()) {
                    resultFileReadLabel.setText("Please, select file first");
                    return;
                }
            }
            fileReadChooser.setVisible(true);
            int option = fileReadChooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                String filename = fileReadChooser.getSelectedFile().getPath();
                ArrayList<String> arrayList = FileReaders.readFile(filename);
                StringBuilder stringBuilder = new StringBuilder("<html><p align=left>");
                for (String s : arrayList) {
                    String result = String.format("%.5f", Expression.calculate(s));
                    stringBuilder.append(s).append(" = ").append(result).append("<br>");
                    if (fileWriterCheckBox.isSelected()) {
                        fileWriters.writeFile(result);
                        if (fileWriters.getClass() == FileWritersTXT.class) {
                            fileWriters.writeFile("\n");
                        }
                    }
                }
                stringBuilder.append("</p></html>");
                resultFileReadLabel.setText(stringBuilder.toString());
            }
        }
        catch (RuntimeException e) {
            resultFileReadLabel.setText(e.getMessage());
        }
    }

    private static void errorLabelCleanEvent() {
        errorLabel.setText("");
    }

    private static void resultLabelCleanEvent() {
        resultOwnEnterLabel.setText("");
    }

    private static void showFileWritersEvent() {
        setVisibleTrue(
                overwriteFileCheckBox,
                TXTButton,
                XMLButton,
                JSONButton,
                stopWritingButton
        );
    }

    private static void hideFileWritersEvent() {
        setVisibleFalse(
                overwriteFileCheckBox,
                TXTButton,
                XMLButton,
                JSONButton,
                stopWritingButton
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

    private static void chooseFileWriteEvent(String format) {
        fileWriteChooser.setVisible(true);
        int option = fileWriteChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            if (!fileWriteChooser.getSelectedFile().getName().substring(
                            fileWriteChooser.getSelectedFile().getName().lastIndexOf('.'))
                    .equals(format)
            ) {
                WrongFormatDialog wrongFormatDialog = new WrongFormatDialog(format);
                wrongFormatDialog.createWrongFormatDialog();
                return;
            }
            try {
                switch (format) {
                    case ".txt" -> fileWriters = new FileWritersTXT (
                            fileWriteChooser.getSelectedFile().getPath(),
                            fileWriters.getOverwrite()
                    );
                    case ".xml" -> fileWriters = new FileWritersXML(
                            fileWriteChooser.getSelectedFile().getPath(),
                            fileWriters.getOverwrite()
                    );
                    case ".json" -> fileWriters = new FileWritersJSON(
                            fileWriteChooser.getSelectedFile().getPath(),
                            fileWriters.getOverwrite()
                    );
                }
                chosenWriteFile.setText(format + " has been chosen");
                overwriteFileCheckBox.setEnabled(false);
                fileWriterCheckBox.setEnabled(false);
                TXTButton.setEnabled(false);
                XMLButton.setEnabled(false);
                JSONButton.setEnabled(false);
                stopWritingButton.setEnabled(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void stopWritingEvent() {
        String filename = fileWriters.getFilename();
        fileWriters.close();
        archiveEncryptEvent(filename);
        chosenWriteFile.setText("");
        overwriteFileCheckBox.setEnabled(true);
        fileWriterCheckBox.setEnabled(true);
        TXTButton.setEnabled(true);
        XMLButton.setEnabled(true);
        JSONButton.setEnabled(true);
        stopWritingButton.setEnabled(false);
    }

    private static class ArchiveEncryptDialog {

        private static final JDialog archiveEncryptDialog = new JDialog(mainWindow, "Archive or Decrypt", Dialog.DEFAULT_MODALITY_TYPE);
        private static final JLabel archiveEncryptLabel = new JLabel();
        private static final JButton archiveButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileWriters = new FileWritersZIP(filename + ".zip");
                fileWriters.writeFile(filename);
                filename = fileWriters.getFilename();
                fileWriters.close();
                archiveEncryptLabel.setText("<html><p align=center>Done<br>" +
                        "Anything else?</p></html>");
            }
        });
        private static final JButton encryptButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileWriters = new FileWritersENC(filename + ".enc");
                fileWriters.writeFile(filename);
                filename = fileWriters.getFilename();
                fileWriters.close();
                archiveEncryptLabel.setText("<html><p align=center>Done<br>" +
                        "Anything else?</p></html>");
            }
        });
        private static final JButton exitButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                archiveEncryptDialog.dispose();
            }
        });
        private static String filename;

        ArchiveEncryptDialog(String filename1) { filename = filename1; }

        private void createArchiveEncryptDialog() {
            setArchiveEncryptSettings();
            setArchiveEncryptLabelSettings();
            setArchiveButtonSettings();
            setEncryptButtonSettings();
            setExitButtonSettings();
            archiveEncryptDialog.add(archiveEncryptLabel);
            archiveEncryptDialog.add(archiveButton);
            archiveEncryptDialog.add(encryptButton);
            archiveEncryptDialog.add(exitButton);
            archiveEncryptDialog.setVisible(true);
        }

        private void setArchiveEncryptSettings() {
            archiveEncryptDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            archiveEncryptDialog.setBounds(SCREEN_SIZE.width / 2 - 200, SCREEN_SIZE.height / 2 - 75, 400, 200);
            archiveEncryptDialog.setLayout(null);
            archiveEncryptDialog.setResizable(false);
        }

        private void setArchiveEncryptLabelSettings() {
            archiveEncryptLabel.setText("<html><p align=center>Do you want to archive<br>" +
                    "or encrypt output file?</p></html>"
            );
            archiveEncryptLabel.setBounds(20, 20, 360, 90);
            archiveEncryptLabel.setHorizontalAlignment(SwingConstants.CENTER);
            archiveEncryptLabel.setVerticalAlignment(SwingConstants.CENTER);
            archiveEncryptLabel.setFont(new Font("Arial", Font.BOLD, 20));

        }

        private void setArchiveButtonSettings() {
            archiveButton.setText("Archive");
            archiveButton.setHorizontalAlignment(SwingConstants.CENTER);
            archiveButton.setVerticalAlignment(SwingConstants.CENTER);
            archiveButton.setBounds(archiveEncryptLabel.getX() + 15, archiveEncryptLabel.getY() + 100, 100, 30);
            archiveButton.setFont(new Font("Arial", Font.BOLD, 15));
            archiveButton.setForeground(new Color(68, 68, 68));
            archiveButton.setFocusable(false);
            archiveButton.setVisible(true);
        }

        private void setEncryptButtonSettings() {
            encryptButton.setText("Encrypt");
            encryptButton.setHorizontalAlignment(SwingConstants.CENTER);
            encryptButton.setVerticalAlignment(SwingConstants.CENTER);
            encryptButton.setBounds(archiveButton.getX() + 110, archiveEncryptLabel.getY() + 100, 100, 30);
            encryptButton.setFont(new Font("Arial", Font.BOLD, 15));
            encryptButton.setForeground(new Color(68, 68, 68));
            encryptButton.setFocusable(false);
            encryptButton.setVisible(true);
        }

        private void setExitButtonSettings() {
            exitButton.setText("Exit");
            exitButton.setHorizontalAlignment(SwingConstants.CENTER);
            exitButton.setVerticalAlignment(SwingConstants.CENTER);
            exitButton.setBounds(encryptButton.getX() + 110, archiveEncryptLabel.getY() + 100, 100, 30);
            exitButton.setFont(new Font("Arial", Font.BOLD, 15));
            exitButton.setForeground(new Color(68, 68, 68));
            exitButton.setFocusable(false);
            exitButton.setVisible(true);
        }
    }

    private static void archiveEncryptEvent(String filename) {
        ArchiveEncryptDialog archiveEncryptDialog = new ArchiveEncryptDialog(filename);
        archiveEncryptDialog.createArchiveEncryptDialog();
    }
}
