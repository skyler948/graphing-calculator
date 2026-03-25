package settings;

import display.Display;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow {

    private JFrame frame;

    private int width, height;
    private Dimension dimension;

    private Settings settings;
    private Display display;

    private Panel mainPanel;

    private Panel[] settingsPanels;
    private JLabel[] settingsLabels;
    private JTextField[] settingsFields;

    private JButton saveButton;

    public SettingsWindow(int width, int height, Settings settings, Display display) {
        this.width = width;
        this.height = height;
        this.settings = settings;
        this.display = display;
    }

    public void createDisplay() {
        dimension = new Dimension(width, height);

        frame = new JFrame("Settings");

        frame.setSize(dimension);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setFocusable(true);

        frame.setVisible(true);

        frame.setAlwaysOnTop(true);

        mainPanel = new Panel();
        mainPanel.setPreferredSize(dimension);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel);

        settingsPanels = new Panel[]{
                new Panel(),
                new Panel(),
                new Panel(),
                new Panel(),
                new Panel()
        };

        settingsLabels = new JLabel[]{
                new JLabel("Scale"),
                new JLabel("minX"),
                new JLabel("maxX"),
                new JLabel("minY"),
                new JLabel("maxY")
        };

        settingsFields = new JTextField[]{
                new JTextField(),
                new JTextField(),
                new JTextField(),
                new JTextField(),
                new JTextField()
        };

        for (int i = 0; i < settingsPanels.length; i++) {
            settingsPanels[i].add(settingsLabels[i]);
            settingsPanels[i].add(settingsFields[i]);

            settingsFields[i].setColumns(10);
            ((AbstractDocument) settingsFields[i].getDocument()).setDocumentFilter(new IntegerDocumentFilter());

            mainPanel.add(settingsPanels[i]);
        }

        settingsFields[0].setText("" + settings.getScale());
        settingsFields[1].setText("" + settings.getMinX());
        settingsFields[2].setText("" + settings.getMaxX());
        settingsFields[3].setText("" + settings.getMinY());
        settingsFields[4].setText("" + settings.getMaxY());

        saveButton = new JButton("Save");

        mainPanel.add(saveButton);

        createActionListeners();
    }

    private void createActionListeners() {
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                settings.setScale(Integer.parseInt(settingsFields[0].getText()));
                settings.setMinX(Integer.parseInt(settingsFields[1].getText()));
                settings.setMaxX(Integer.parseInt(settingsFields[2].getText()));
                settings.setMinY(Integer.parseInt(settingsFields[3].getText()));
                settings.setMaxY(Integer.parseInt(settingsFields[4].getText()));

                display.recreateDisplay();

                settings.writeChangesToConfigFile();
            }

        });
    }

    public boolean isDisplayOpen() {
        if (frame == null) return false;
        return frame.isVisible();
    }

}
