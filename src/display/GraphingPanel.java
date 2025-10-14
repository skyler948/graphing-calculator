package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import calculator.EquationParser;

public class GraphingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Display display;
	
	private int relOriginX, relOriginY;
	private int xOffs = 0, yOffs = 0;
	
	private int minX = -14, maxX = 14;
	private int minY = -10, maxY = 10;
	
	private int domain;
	private int range;
	
	private int xRatio = 7, yRatio = 5;
	double zoom = 2;
	
	private int resolution = 8;
	private double invResolution = Math.pow(resolution, -1);
	
	private int vertSpace = 0, horizSpace = 0;
	
	private double xIntercept = 0;
	
	private double[] points;
	
	private String equation = "";
	
	public GraphingPanel(Display display) {
		this.display = display;
		
		setBackground(Color.black);
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(display.getDisplayWidth(), display.getDisplayHeight());
    }
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Set variables
		setDomainAndRange();
		setRelativeOrigins();
		generatePoints();
		
		// Draw
		g.setColor(Color.white);
        g.setFont(display.getUIFont());
		
		// Y axis
		g.drawLine(relOriginX, 0, relOriginX, getHeight());
		for (int y = minY; y <= maxY; y++) {
			g.drawLine(relOriginX - 5, relOriginY + (y * vertSpace), relOriginX + 5, relOriginY + (y * vertSpace));
		}
		
		// X axis
		g.drawLine(0, relOriginY, getWidth(), relOriginY);
		for (int x = minX; x <= maxX; x++) {
			g.drawLine(relOriginX + (x * horizSpace), relOriginY - 5, relOriginX + (x * horizSpace), relOriginY + 5);
		}
		
		// Draw points
		g.setColor(Color.red);
		int i = 0;
		for (double x = minX - 1; x < maxX + 1; x += invResolution) {
			if (i < points.length - 1) {
				if (Double.isFinite(points[i]) && Double.isFinite(points[i + 1])) {
					g.drawLine(relOriginX + (int) (x * horizSpace), relOriginY - ((int) (points[i] * vertSpace)), relOriginX + (int) ((x + invResolution) * horizSpace), relOriginY - ((int) (points[i + 1] * vertSpace)));
				}
			}
			i++;
		}
		
		// Text information
		g.setColor(Color.white);
		if (Double.isFinite(xIntercept)) {
			g.drawString("x-intercept: " + xIntercept, 5, display.getUIFont().getSize());
		} else {
			g.drawString("x-intercept: undefined", 5, display.getUIFont().getSize());
		}
		g.drawString("zoom: " + zoom, 5, display.getUIFont().getSize() * 2);
		
		// End
		g.dispose();
	}
	
	private void setDomainAndRange() {
		domain = Math.abs(minX - maxX);
		range = Math.abs(minY - maxY);
	}
	
	public void doubleXYRange() {
		minX -= xRatio;
		maxX += xRatio;
		minY -= yRatio;
		maxY += yRatio;
		zoom++;
		setDomainAndRange();
	}
	
	public void halfXYRange() {
		minX += xRatio;
		maxX -= xRatio;
		minY += yRatio;
		maxY -= yRatio;
		zoom--;
		if (zoom <= 0) {
			maxX = 7;
			minX = -7;
			maxY = 5;
			minY = -5;
			zoom = 1;
		}
		setDomainAndRange();
	}
	
	private void setRelativeOrigins() {
		// Origin points relative to the panel
		relOriginX = (getWidth() / 2) + xOffs;
		relOriginY = (getHeight() / 2) + yOffs;
		
		// Spaces of domain and range relative to panel size
		vertSpace = getHeight() / range;
		horizSpace = getWidth() / domain;
	}
	
	public void generatePoints() {
		points = new double[(domain * resolution) + (resolution * 2)];
		int i = 0;
		
		// Generate points
		for (double x = minX - 1; x <= maxX + 1; x += invResolution) {
			points[i] = EquationParser.parseEquationAtX(equation, x);
			i++;
			if (i >= points.length) break;
		}
		
		// Get X-Intercept
		xIntercept = EquationParser.parseEquationAtX(equation, 0);
	}
	
	public int getRelOriginX() {
		return relOriginX;
	}
	
	public int getRelOriginY() {
		return relOriginY;
	}
	
	public String getEquation() {
		return equation;
	}
	
	public void setEquation(String equation) {
		this.equation = equation;
	}

}
