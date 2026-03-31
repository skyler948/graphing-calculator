package calculator;

import display.GraphingPanel;

import java.awt.*;

public class Graph {

    private String equation;
    private double[] points;
    private Color color;

    private GraphingPanel panel;

    private double x = 0.0;

    private boolean showIntercepts = true;
    private final byte POINT_SIZE = 12;

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

        double yIntercept = getY(0);

        for (int i = 0; i < points.length - 1; i++) {
            if (Double.isFinite(points[i]) && Double.isFinite(points[i + 1])) {
                int x1 = (int) ((x * panel.getHorizSpace()) - panel.getHorizSpace());
                int y1 = panel.getRelOriginY() - (int) (points[i] * panel.getVertSpace());

                int x2 = (int) (((x + panel.getInvResolution()) * panel.getHorizSpace()) - panel.getHorizSpace());
                int y2 = panel.getRelOriginY() - (int) (points[i + 1] * panel.getVertSpace());

                g.drawLine(x1, y1, x2, y2);

                if (showIntercepts && points[i] == yIntercept) {
                    g.fillOval(x1 - (POINT_SIZE / 2), y1 - (POINT_SIZE / 2), POINT_SIZE, POINT_SIZE);

                    g.setColor(Color.white);
                    g.drawString("" + yIntercept, x1 + 10, y1 + panel.getDisplay().getUIFont().getSize());
                    g.setColor(color);
                }
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

    public byte getPointSize() {
        return POINT_SIZE;
    }

    public void setShowIntercepts(boolean showIntercepts) {
        this.showIntercepts = showIntercepts;
    }

}
