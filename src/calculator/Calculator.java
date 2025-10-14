package calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import display.Display;

public class Calculator {
	
	private Display display;
	
	public Calculator() {
		display = new Display(1024, 768);
	}

}
