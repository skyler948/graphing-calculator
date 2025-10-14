package display;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Display {
	
	private int width, height;
	private final String TITLE = "Graphing Calculator";
	
	private Dimension dimension;
	
	private JFrame frame;
	private JPanel panel;
	
	private JPanel panelUI;
	private GraphingPanel panelGraph;

    private int scale = 1;
    private Font uiFont;
	
	private JTextField field;
	private JButton graphButton;
	
	private JButton zoomOutButton, zoomInButton;

    private JButton scaleButton;

    private GraphicsDevice gd;
    private int displayWidth, displayHeight;
	
	public Display(int width, int height) {
		this.width = width;
		this.height = height;
		
		dimension = new Dimension(width, height);

        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        displayWidth = gd.getDisplayMode().getWidth();
        displayHeight = gd.getDisplayMode().getHeight();
		
		createDisplay();
	}
	
	private void createDisplay() {
        uiFont = new Font("Arial", Font.PLAIN, 12 * scale);

		// Display parameters
		frame = new JFrame(TITLE);
		
		frame.setSize(dimension);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setFocusable(true);
		
		frame.setVisible(true);
		
		// Panel parameters
		panel = new JPanel();
		panel.setPreferredSize(dimension);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.black);
		
		// Create display elements
		panelUI = new JPanel();
		panelGraph = new GraphingPanel(this);
		
		zoomOutButton = new JButton("-");
		zoomOutButton.setPreferredSize(new Dimension(30 * scale, 20 * scale));
		zoomOutButton.setBorder(new LineBorder(Color.gray));
        zoomOutButton.setFont(uiFont);
		
		zoomInButton = new JButton("+");
		zoomInButton.setPreferredSize(new Dimension(30 * scale, 20 * scale));
		zoomInButton.setBorder(new LineBorder(Color.gray));
        zoomInButton.setFont(uiFont);
		
		panelUI.add(zoomInButton);
		panelUI.add(zoomOutButton);
		
		field = new JTextField(25);
        field.setPreferredSize(new Dimension(300 * scale, 20 * scale));
        field.setFont(uiFont);
		panelUI.add(field);
		
		graphButton = new JButton("Graph!");
		graphButton.setPreferredSize(new Dimension(80 * scale, 20 * scale));
        graphButton.setFont(uiFont);
		panelUI.add(graphButton);

        scaleButton = new JButton("⚙");
        scaleButton.setPreferredSize(new Dimension(30 * scale, 20 * scale));
        scaleButton.setBorder(new LineBorder(Color.gray));
        scaleButton.setFont(uiFont);
        panelUI.add(scaleButton);
		
		// Add display elements
		frame.add(panel);

		panel.add(panelUI);
		panel.add(panelGraph);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Add action listeners
        createActionListeners();
	}

    private void createActionListeners() {
        // Get what is in the text field from button press
        graphButton.addActionListener(new ActionListener() {

            // On click of button
            public void actionPerformed(ActionEvent a) {
                panelGraph.setEquation(field.getText());
                panelGraph.repaint();
            }

        });

        // Zoom in/out buttons
        zoomInButton.addActionListener(new ActionListener() {

            // Zoom in
            public void actionPerformed(ActionEvent a) {
                panelGraph.halfXYRange();
                panelGraph.repaint();
            }

        });

        zoomOutButton.addActionListener(new ActionListener() {

            // Zoom out
            public void actionPerformed(ActionEvent a) {
                panelGraph.doubleXYRange();
                panelGraph.repaint();
            }

        });

        scaleButton.addActionListener(new ActionListener() {

            // Change scale
            public void actionPerformed(ActionEvent e) {
                increaseScale();
                recreateDisplay();
            }

        });
    }

    public void recreateDisplay() {
        // Delete current display
        frame.dispose();

        // Recreate it
        createDisplay();
    }
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public JPanel getPanelUI() {
		return panelUI;
	}
	
	public GraphingPanel getPanelGraph() {
		return panelGraph;
	}
	
	public JTextField getTextField() {
		return field;
	}
	
	public JButton getGraphButton() {
		return graphButton;
	}
	
	public JButton getZoomOutButton() {
		return zoomOutButton;
	}
	
	public JButton getZoomInButton() {
		return zoomInButton;
	}

    public int getScale() {
        return scale;
    }

    public void increaseScale() {
        scale++;
        if (scale > 5) scale = 1;
    }

    public Font getUIFont() {
        return uiFont;
    }

    public GraphicsDevice getGraphicsDevice() {
        return gd;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public JButton getScaleButton() {
        return scaleButton;
    }

}
