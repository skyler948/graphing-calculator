package calculator;

public class EquationParser {
	
	public static double parseEquationAtX(String equation, double value) {
		if (!equation.isEmpty()) {
			// Split equation
			String[] data = equation.split(" ");
			
			// Get values of each part
			double[] part = new double[(data.length / 2) + 1];
			int j = 0;
			for (int i = 0; i < data.length; i += 2) {
				int xPos = data[i].indexOf('x');
				int powPos = data[i].indexOf('^');
				
				boolean constant = false;
				
				double coefficient = 0.0;
				double exponent = 0.0;
				
				// Check if the x position is at 0, if so there is no coefficient
				if (xPos == 0) {
					// No coefficient
					coefficient = 1.0;
				} else if (xPos == -1) {
					// Constant
					constant = true;
					
					// Check if theres a power
					if (powPos != -1) {
						coefficient = Double.parseDouble(data[i].substring(0, powPos));
					} else {
						coefficient = Double.parseDouble(data[i]);
					}
				} else {
					// Coefficient
					coefficient = Double.parseDouble(data[i].substring(0, xPos));
				}
				
				// Check if the ^ position is not -1. If so, there is no power
				if (powPos != -1) {
					exponent = Double.parseDouble(data[i].substring(powPos + 1));
				} else {
					exponent = 1.0;
				}
				
				// Do calculations for set position
				if (!constant) {
					part[j] = coefficient * (Math.pow(value, exponent));
				} else {
					part[j] = Math.pow(coefficient, exponent);
				}
				
				j++;
			}
			
			double result = part[0];
			j = 0;
			
			for (int i = 0; i < data.length; i++) {
				if (data[i].equals("+")) {
					result = result + part[j + 1];
					j++;
				} else if (data[i].equals("-")) {
					result = result - part[j + 1];
					j++;
				}
			}
			
			return result;
		}
		return 0.0;
	}

}
