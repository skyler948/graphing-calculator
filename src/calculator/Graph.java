package calculator;

import display.GraphingPanel;

import java.awt.*;

public class Graph {

    private String equation;
    private double[] points;
    private Color color;

    private GraphingPanel panel;

    private double x = 0.0;

    public Graph(String equation, Color color, GraphingPanel panel) {
        this.equation = equation;
        this.color = color;
        this.panel = panel;
    }

    public void generatePoints() {
        points = new double[(panel.getDomain() * panel.getResolution()) + (panel.getResolution() * 2)];
        int i = 0;

        // Generate points
        for (double x = panel.getMinX() - 1; x <= panel.getMaxX() + 1; x += panel.getInvResolution()) {
            points[i] = EquationParser.parseEquationAtX(equation, x);
            i++;
            if (i >= points.length) break;
        }
    }

    public void render(Graphics g) {
        g.setColor(color);

        for (int i = 0; i < points.length - 1; i++) {
            if (Double.isFinite(points[i]) && Double.isFinite(points[i + 1])) {
                g.drawLine((int) ((x * panel.getHorizSpace()) - panel.getHorizSpace()), panel.getRelOriginY() - (int) (points[i] * panel.getVertSpace()),
                        (int) (((x + panel.getInvResolution()) * panel.getHorizSpace()) - panel.getHorizSpace()), panel.getRelOriginY() - (int) (points[i + 1] * panel.getVertSpace()));
            }
            x += panel.getInvResolution();
        }

        x = 0.0;
    }

    public double getY(double x) {
        return EquationParser.parseEquationAtX(equation, x);
    }

    public double getYIntercept() {
        return getY(0);
    }

    public String getEquation() {
        return equation;
    }

    public Color getColor() {
        return color;
    }

}
