package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import calculator.EquationParser;
import calculator.Graph;
import settings.Settings;

public class GraphingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Display display;
	
	private int relOriginX, relOriginY;
	private int xOffs, yOffs;
	
	private int minX, maxX;
	private int minY, maxY;

	private int domain;
	private int range;

    private final int ZOOM_CONSTANT = 2;
    private boolean justZoomed = false;
	
	private int resolution = 8;
	private double invResolution = Math.pow(resolution, -1);
	
	private double vertSpace = 0, horizSpace = 0;
	
	private double xIntercept = 0;

    private ArrayList<Graph> graphs;

    private Settings settings;
	
	public GraphingPanel(Display display, Settings settings) {
		this.display = display;
        this.settings = settings;

        graphs = new ArrayList<>();

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
        for (Graph graph : graphs) {
            graph.generatePoints();
        }
		
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
        for (Graph graph : graphs) {
            graph.render(g);
        }
		
		// End
		g.dispose();
	}

    public void getConstraints() {
        minX = settings.getMinX();
        maxX = settings.getMaxX();
        minY = settings.getMinY();
        maxY = settings.getMaxY();
    }
	
	private void setDomainAndRange() {
		domain = Math.abs(minX - maxX);
		range = Math.abs(minY - maxY);
	}
	
	public void doubleXYRange() {
        // Change perspectives
		minX -= ZOOM_CONSTANT;
		maxX += ZOOM_CONSTANT;
		minY -= ZOOM_CONSTANT;
		maxY += ZOOM_CONSTANT;
		setDomainAndRange();
	}
	
	public void halfXYRange() {
        // Change perspective
        minX += ZOOM_CONSTANT;
        maxX -= ZOOM_CONSTANT;
        minY += ZOOM_CONSTANT;
        maxY -= ZOOM_CONSTANT;

        checkValueValidity();

		setDomainAndRange();
	}

    private void checkValueValidity() {
        if (minX == 0 && maxX == 0) {
            minX = -ZOOM_CONSTANT;
            maxX = ZOOM_CONSTANT;
        }

        if (minY == 0 && maxY == 0) {
            minY = -ZOOM_CONSTANT;
            maxY = ZOOM_CONSTANT;
        }

        if (minX > maxX) {
            int tX = maxX;
            maxX = minX;
            minX = tX;
        }

        if (minY > maxY) {
            int tY = maxY;
            maxY = minY;
            minY = tY;
        }
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
	
	public int getRelOriginX() {
		return relOriginX;
	}
	
	public int getRelOriginY() {
		return relOriginY;
	}

    public void removeRecentGraph() {
        if (!graphs.isEmpty()) {
            graphs.removeLast();
        }
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getXOffs() {
        return xOffs;
    }

    public int getYOffs() {
        return yOffs;
    }

    public int getDomain() {
        return domain;
    }

    public int getRange() {
        return range;
    }

    public int getResolution() {
        return resolution;
    }

    public double getInvResolution() {
        return invResolution;
    }

    public double getVertSpace() {
        return vertSpace;
    }

    public double getHorizSpace() {
        return horizSpace;
    }

    public void addGraph(Graph graph) {
        graphs.add(graph);
    }

    public void removeGraph(Graph graph) {
        graphs.remove(graph);
    }

    public ArrayList<Graph> getGraphs() {
        return graphs;
    }

}
