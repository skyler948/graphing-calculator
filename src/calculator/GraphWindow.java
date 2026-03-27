package calculator;

import display.Display;
import settings.IntegerDocumentFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphWindow {

    private JFrame frame;

    private int width, height;
    private Dimension dimension;

    private Display display;

    private Panel mainPanel;

    private Panel[] graphPanels;
    private JLabel[] graphLabels;
    private JButton[] removeButtons;

    private Panel[] inputPanels;
    private JLabel[] inputLabels;
    private JTextField[] inputFields;
    private JButton[] inputButtons;
    private JLabel[] resultLabels;

    private Panel[] connectingPanel;

    private IntegerDocumentFilter inputFilter;

    private JLabel noGraphsLabel;

    public GraphWindow(int width, int height, Display display) {
        this.width = width;
        this.height = height;
        this.display = display;

        inputFilter = new IntegerDocumentFilter();
        inputFilter.characterList = new char[]{'-', '.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    }

    public void createDisplay() {
        frame = new JFrame("Graph Viewer");

        dimension = new Dimension(width, height);

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

        createPanelElements();
    }

    private void createPanelElements() {
        graphPanels = new Panel[display.getPanelGraph().getGraphs().size()];
        graphLabels = new JLabel[graphPanels.length];
        removeButtons = new JButton[graphPanels.length];

        inputPanels = new Panel[graphPanels.length];
        inputLabels = new JLabel[graphPanels.length];
        inputFields = new JTextField[graphPanels.length];
        inputButtons = new JButton[graphPanels.length];
        resultLabels = new JLabel[graphPanels.length];

        connectingPanel = new Panel[graphPanels.length];

        if (graphPanels.length == 0) {
            noGraphsLabel = new JLabel("No graphs!");
            noGraphsLabel.setFont(display.getUIFont());
            mainPanel.add(noGraphsLabel);
        }

        for (int i = 0; i < graphPanels.length; i++) {
            connectingPanel[i] = new Panel();
            connectingPanel[i].setLayout(new BoxLayout(connectingPanel[i], BoxLayout.LINE_AXIS));
            mainPanel.add(connectingPanel[i]);

            graphPanels[i] = new Panel();
            connectingPanel[i].add(graphPanels[i]);

            if (Double.isFinite(display.getPanelGraph().getGraphs().get(i).getYIntercept())) {
                graphLabels[i] = new JLabel("y = " + display.getPanelGraph().getGraphs().get(i).getEquation() +
                        "| y-intercept: " + display.getPanelGraph().getGraphs().get(i).getYIntercept() + " | ");
            } else {
                graphLabels[i] = new JLabel("y = " + display.getPanelGraph().getGraphs().get(i).getEquation() +
                        "| y-intercept: undefined | ");
            }
            graphLabels[i].setFont(display.getUIFont());
            graphPanels[i].add(graphLabels[i]);

            removeButtons[i] = new JButton("Remove");
            removeButtons[i].setFont(display.getUIFont());
            graphPanels[i].add(removeButtons[i]);

            int finalI = i;
            Graph graph = display.getPanelGraph().getGraphs().get(i);
            removeButtons[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    display.getPanelGraph().removeGraph(graph);
                    display.getPanelGraph().repaint();

                    mainPanel.remove(connectingPanel[finalI]);
                }

            });

            inputPanels[i] = new Panel();
            connectingPanel[i].add(inputPanels[i]);

            inputLabels[i] = new JLabel("Input x value:");
            inputLabels[i].setFont(display.getUIFont());
            inputPanels[i].add(inputLabels[i]);

            inputFields[i] = new JTextField();
            inputFields[i].setFont(display.getUIFont());
            inputFields[i].setColumns(4);
            inputPanels[i].add(inputFields[i]);

            ((AbstractDocument) inputFields[i].getDocument()).setDocumentFilter(inputFilter);

            inputButtons[i] = new JButton("Go!");
            inputButtons[i].setFont(display.getUIFont());
            inputPanels[i].add(inputButtons[i]);

            resultLabels[i] = new JLabel("y = ");
            resultLabels[i].setFont(display.getUIFont());
            inputPanels[i].add(resultLabels[i]);

            inputButtons[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    double value = display.getPanelGraph().getGraphs().get(finalI).getY(Double.parseDouble(inputFields[finalI].getText()));
                    if (Double.isFinite(value)) {
                        resultLabels[finalI].setText("y = " + value);
                    } else {
                        resultLabels[finalI].setText("y = undefined");
                    }
                }

            });
        }
    }

    public boolean isDisplayOpen() {
        if (frame == null) return false;
        return frame.isVisible();
    }

}
