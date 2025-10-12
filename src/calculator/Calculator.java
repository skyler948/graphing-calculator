package calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import display.Display;

public class Calculator {
	
	private Display display;
	
	public Calculator() {
		display = new Display(1024, 768);
		
		// Get what is in the text field from button press
		display.getButton().addActionListener(new ActionListener() {
			
			// On click of button
			public void actionPerformed(ActionEvent a) {
				display.getPanelGraph().setEquation(display.getTextField().getText());
				display.getPanelGraph().repaint();
			}
			
		});
	}

}
