package calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import display.Display;

public class Calculator {
	
	private Display display;
	
	public Calculator() {
		display = new Display(1024, 768);
		
		// Get what is in the text field from button press
		display.getGraphButton().addActionListener(new ActionListener() {
			
			// On click of button
			public void actionPerformed(ActionEvent a) {
				display.getPanelGraph().setEquation(display.getTextField().getText());
				display.getPanelGraph().repaint();
			}
			
		});
		
		// Zoom in/out buttons
		display.getZoomInButton().addActionListener(new ActionListener() {

			// Zoom in
			public void actionPerformed(ActionEvent a) {
				display.getPanelGraph().halfXYRange();
				display.getPanelGraph().repaint();
			}
			
		});
		
		display.getZoomOutButton().addActionListener(new ActionListener() {

			// Zoom out
			public void actionPerformed(ActionEvent a) {
				display.getPanelGraph().doubleXYRange();
				display.getPanelGraph().repaint();
			}
			
		});
		
		display.getFrame().setExtendedState(JFrame.NORMAL);
	}

}
