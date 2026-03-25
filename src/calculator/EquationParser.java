package calculator;

import java.util.Locale;

public class EquationParser {

    // This is so messy lol

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
					
					// Check if there's a power
					if (powPos != -1) {
                        try {
                            coefficient = Double.parseDouble(data[i].substring(0, powPos));
                        } catch (NumberFormatException e) {
                            coefficient = getSpecialConstant(data[i].substring(0, powPos));
                        }
					} else {
                        try {
                            coefficient = Double.parseDouble(data[i]);
                        } catch (NumberFormatException e) {
                            coefficient = getSpecialConstant(data[i]);
                        }
					}
				} else {
					// Coefficient
                    try {
                        coefficient = Double.parseDouble(data[i].substring(0, xPos));
                    } catch (NumberFormatException e) {
                        coefficient = getSpecialConstant(data[i].substring(0, xPos));
                    }
				}
				
				// Check if the ^ position is not -1. If so, there is no power
				if (powPos != -1) {
                    try {
                        exponent = Double.parseDouble(data[i].substring(powPos + 1));
                    } catch (NumberFormatException e) {
                        exponent = getSpecialConstant(data[i].substring(powPos + 1));
                    }
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

            for (String d : data) {
                if (d.equals("+")) {
                    result = result + part[j + 1];
                    j++;
                } else if (d.equals("-")) {
                    result = result - part[j + 1];
                    j++;
                }
            }
			
			return result;
		}
		return 0.0;
	}

    private static double getSpecialConstant(String constant) {
        String formatted = constant.toLowerCase(Locale.ROOT).trim();

        return switch (formatted) {
            case "e" -> Math.E;
            case "pi" -> Math.PI;
            case "tau" -> Math.TAU;
            case "phi" -> 1.61803398874989484820;
            default -> 1.0;
        };
    }

}
