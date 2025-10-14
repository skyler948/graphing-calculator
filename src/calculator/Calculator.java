package calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import display.Display;
import settings.Settings;

public class Calculator {
	
	private Display display;
    private Settings settings;
	
	public Calculator() {
        settings = new Settings();
		display = new Display(1024, 768, settings);
	}

}
