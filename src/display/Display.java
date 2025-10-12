package display;

import java.awt.Color;
import java.awt.Dimension;

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
	
	private JTextField field;
	private JButton graphButton;
	
	private JButton zoomOutButton, zoomInButton;
	
	public Display(int width, int height) {
		this.width = width;
		this.height = height;
		
		dimension = new Dimension(width, height);
		
		createDisplay();
	}
	
	private void createDisplay() {
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
		zoomOutButton.setPreferredSize(new Dimension(30, 20));
		zoomOutButton.setBorder(new LineBorder(Color.gray));
		
		zoomInButton = new JButton("+");
		zoomInButton.setPreferredSize(new Dimension(30, 20));
		zoomInButton.setBorder(new LineBorder(Color.gray));
		
		panelUI.add(zoomInButton);
		panelUI.add(zoomOutButton);
		
		field = new JTextField(25);
		panelUI.add(field);
		
		graphButton = new JButton("Graph!");
		graphButton.setPreferredSize(new Dimension(80, 20));
		panelUI.add(graphButton);
		
		// Add display elements
		frame.add(panel);

		panel.add(panelUI);
		panel.add(panelGraph);
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

}
