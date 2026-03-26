package display;

import settings.Settings;
import settings.SettingsWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private Dimension defaultButtonSize;
	
	private JFrame frame;
	private JPanel panel;
	
	private JPanel panelUI;
	private GraphingPanel panelGraph;

    private int scale;
    private Font uiFont;
	
	private JTextField field;
	private JButton graphButton;

    private JButton graphRemoveButton;
	
	private JButton zoomOutButton, zoomInButton;

    private JButton settingsButton;

    private GraphicsDevice gd;
    private int displayWidth, displayHeight;

    private Settings settings;

    private SettingsWindow settingsWindow;
	
	public Display(int width, int height, Settings settings) {
		this.width = width;
		this.height = height;
        this.settings = settings;
		
		dimension = new Dimension(width, height);

        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        displayWidth = gd.getDisplayMode().getWidth();
        displayHeight = gd.getDisplayMode().getHeight();

        settingsWindow = new SettingsWindow(300, 400, settings, this);
		
		createDisplay();
	}
	
	private void createDisplay() {
        scale = settings.getScale();

        uiFont = new Font("Arial", Font.PLAIN, 12 * scale);
        defaultButtonSize = new Dimension(30 * scale, 20 * scale);

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
		panelGraph = new GraphingPanel(this, settings);
		
		zoomOutButton = new JButton("-");
		zoomOutButton.setPreferredSize(defaultButtonSize);
		zoomOutButton.setBorder(new LineBorder(Color.gray));
        zoomOutButton.setFont(uiFont);
		
		zoomInButton = new JButton("+");
		zoomInButton.setPreferredSize(defaultButtonSize);
		zoomInButton.setBorder(new LineBorder(Color.gray));
        zoomInButton.setFont(uiFont);

        graphRemoveButton = new JButton("✖");
        graphRemoveButton.setPreferredSize(defaultButtonSize);
        graphRemoveButton.setBorder(new LineBorder(Color.gray));
        graphRemoveButton.setFont(uiFont);
		
		panelUI.add(zoomInButton);
		panelUI.add(zoomOutButton);

        panelUI.add(graphRemoveButton);
		
		field = new JTextField(25);
        field.setPreferredSize(new Dimension(300 * scale, 20 * scale));
        field.setFont(uiFont);
		panelUI.add(field);
		
		graphButton = new JButton("Graph!");
		graphButton.setPreferredSize(new Dimension(80 * scale, 20 * scale));
        graphButton.setFont(uiFont);
		panelUI.add(graphButton);

        settingsButton = new JButton("⚙");
        settingsButton.setPreferredSize(defaultButtonSize);
        settingsButton.setBorder(new LineBorder(Color.gray));
        settingsButton.setFont(uiFont);
        panelUI.add(settingsButton);
		
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

        graphRemoveButton.addActionListener(new ActionListener() {

            // Remove most recent graph
            public void actionPerformed(ActionEvent e) {
                panelGraph.removeRecentGraph();
                panelGraph.repaint();
            }

        });

        settingsButton.addActionListener(new ActionListener() {

            // Change scale
            public void actionPerformed(ActionEvent e) {
                if (!settingsWindow.isDisplayOpen()) {
                    settingsWindow.createDisplay();
                    settingsWindow.updateFields();
                }
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
        settings.setScale(scale);
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

    public JButton getSettingsButton() {
        return settingsButton;
    }

}
