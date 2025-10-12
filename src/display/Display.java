package display;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Display {
	
	private int width, height;
	private final String TITLE = "Graphing Calculator";
	
	private Dimension dimension;
	
	private JFrame frame;
	private JPanel panel;
	
	private JPanel panelUI;
	private GraphingPanel panelGraph;
	
	private JTextField field;
	private JButton button;
	
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
		
		frame.setVisible(true);
		
		// Panel parameters
		panel = new JPanel();
		panel.setPreferredSize(dimension);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.black);
		
		// Create display elements
		panelUI = new JPanel();
		panelGraph = new GraphingPanel(this);
		
		field = new JTextField(25);
		panelUI.add(field);
		
		button = new JButton("Graph!");
		button.setPreferredSize(new Dimension(80, 20));
		panelUI.add(button);
		
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
	
	public JButton getButton() {
		return button;
	}

}
