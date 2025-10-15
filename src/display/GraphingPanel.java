package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import calculator.EquationParser;
import settings.Settings;

public class GraphingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Display display;
	
	private int relOriginX, relOriginY;
	private int xOffs, yOffs;
	
	private int minX, maxX;
	private int minY, maxY;

    private int minXZoom, maxXZoom;
    private int minYZoom, maxYZoom;

	private int domain;
	private int range;

	double zoom = 2;
	
	private int resolution = 8;
	private double invResolution = Math.pow(resolution, -1);
	
	private double vertSpace = 0, horizSpace = 0;
	
	private double xIntercept = 0;
	
	private double[] points;
	
	private String equation = "";

    private Settings settings;
	
	public GraphingPanel(Display display, Settings settings) {
		this.display = display;
        this.settings = settings;
        getConstraints();
		
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
        if (minX < 0 || maxX > 0) {
            g.drawLine(relOriginX, 0, relOriginX, getHeight());
            for (int y = 0; y <= range; y++) {
                if (y != range) {
                    g.drawLine(relOriginX - 5, (int) (y * vertSpace), relOriginX + 5, (int) (y * vertSpace));
                } else {
                    g.drawLine(relOriginX - 5, (int) (y * vertSpace) - 1, relOriginX + 5, (int) (y * vertSpace) - 1);
                }
            }
        }
		
		// X axis
        if (minY < 0 || maxY > 0) {
            g.drawLine(0, relOriginY, getWidth(), relOriginY);
            for (int x = 0; x <= domain; x++) {
                g.drawLine((int) (x * horizSpace), relOriginY - 5, (int) (x * horizSpace), relOriginY + 5);
            }
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

    public void getConstraints() {
        minX = settings.getMinX();
        maxX = settings.getMaxX();
        minY = settings.getMinY();
        maxY = settings.getMaxY();

        minXZoom = Math.abs(minX) / 2;
        maxXZoom = Math.abs(maxX) / 2;
        minYZoom = Math.abs(minY) / 2;
        maxYZoom = Math.abs(maxY) / 2;
    }
	
	private void setDomainAndRange() {
		domain = Math.abs(minX - maxX);
		range = Math.abs(minY - maxY);
	}
	
	public void doubleXYRange() {
		minX -= minXZoom;
		maxX += maxXZoom;
		minY -= minYZoom;
		maxY += maxYZoom;
		zoom++;
		setDomainAndRange();
	}
	
	public void halfXYRange() {
        zoom--;
        if (zoom != 0) {
            minX += minXZoom;
            maxX -= maxXZoom;
            minY += minYZoom;
            maxY -= maxYZoom;
        } else {
            zoom = 1;
        }
		setDomainAndRange();
	}
	
	private void setRelativeOrigins() {
		// Origin points relative to the panel
		relOriginX = (getWidth() / 2);
		relOriginY = (getHeight() / 2);
		
		// Spaces of domain and range relative to panel size
        horizSpace = (double) getWidth() / domain;
		vertSpace = (double) getHeight() / range;

        // Set offsets
        xOffs = (int) (((maxX + minX) * horizSpace) / 2);
        yOffs = (int) (((maxY + minY) * vertSpace) / 2);

        // Offset origin
        relOriginX -= xOffs;
        relOriginY += yOffs;
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
