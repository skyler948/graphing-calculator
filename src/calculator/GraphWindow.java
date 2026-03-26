package calculator;

import display.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GraphWindow {

    private JFrame frame;

    private int width, height;
    private Dimension dimension;

    private Display display;

    private Panel mainPanel;

    private Panel[] graphPanels;
    private JLabel[] graphLabels;
    private JButton[] removeButtons;

    private JLabel noGraphsLabel;

    public GraphWindow(int width, int height, Display display) {
        this.width = width;
        this.height = height;
        this.display = display;
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

        if (graphPanels.length == 0) {
            noGraphsLabel = new JLabel("No graphs!");
            noGraphsLabel.setFont(display.getUIFont());
            mainPanel.add(noGraphsLabel);
        }

        for (int i = 0; i < graphPanels.length; i++) {
            graphPanels[i] = new Panel();
            mainPanel.add(graphPanels[i]);

            if (Double.isFinite(display.getPanelGraph().getGraphs().get(i).getXIntercept())) {
                graphLabels[i] = new JLabel("y = " + display.getPanelGraph().getGraphs().get(i).getEquation() +
                        "| x-intercept: " + display.getPanelGraph().getGraphs().get(i).getXIntercept() + " | ");
            } else {
                graphLabels[i] = new JLabel("y = " + display.getPanelGraph().getGraphs().get(i).getEquation() +
                        "| x-intercept: undefined | ");
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

                    mainPanel.remove(graphPanels[finalI]);
                }

            });
        }
    }

    public boolean isDisplayOpen() {
        if (frame == null) return false;
        return frame.isVisible();
    }

}
