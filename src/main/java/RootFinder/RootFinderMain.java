package RootFinder;

import LinkedList.LinkedList;
import RootFinder.Functions.Function;
import RootFinder.Functions.Euler;
import RootFinder.Functions.Logarithm;
import RootFinder.Functions.Quadratic;
import javax.swing.JCheckBox;

/**
 * Main class
 *
 * @author Abdullah
 */
public class RootFinderMain {
    
    private final String[] functionsAvailableLabels = {"f(x) = x - x^2", "f(x) = ln(x+1) + 1", "f(x) = e^x - 3x"}; // Functions in JComboBox
    private final String[] functionsAvailableTags = {"quadratic", "logarithm", "euler"}; // Map above functions to recognizable tag
    
    private String selectedFunction; // Value of dropdown menu
    private Function currentFunction = new Quadratic(false); // Function selected by the user
    
    // GUI
    private final RootFinderGUI gui;
    
    public static void main(String[] args) {
        RootFinderMain rootFinder = new RootFinderMain();
    }
    
    /**
     * Main program
     */
    public RootFinderMain() {
        // Set up GUI
        gui = new RootFinderGUI(functionsAvailableLabels, currentFunction);
        
        /**
         * ActionListeners
         */
        
        // Update the chart when user changes function using dropdown menu
        gui.functionsDropdown.addActionListener(e -> {
            selectedFunction = functionsAvailableTags[gui.functionsDropdown.getSelectedIndex()];
            switch (selectedFunction) {
                case "quadratic":
                    currentFunction = new Quadratic(false);
                    break;
                case "logarithm":
                    currentFunction = new Logarithm(false);
                    break;
                case "euler":
                    currentFunction = new Euler(false);
                    break;
            }
            gui.updateChart(currentFunction);
        });
        
        // Numerical method(s) selection
        gui.numericalMethodsBtnGroup.forEach((JCheckBox checkbox) -> {
            checkbox.addActionListener(e -> {
                /**
                 * When the user changes the status of a JCheckBox,
                 * check if bisection and/or secant are still selected.
                 * If they are, the x1 input is still required,
                 * otherwise x1 can be disabled.
                 */
                if(checkbox.getActionCommand().equals("secant") || checkbox.getActionCommand().equals("bisection")) {
                    if(checkbox.isSelected()) {
                        gui.requireX1++;
                        gui.x1.setEnabled(true);
                        if(gui.x1.getText().equals("N/A")) gui.x1.setText("");
                    } else {
                        gui.requireX1--;
                        if(gui.requireX1 < 1) {
                            gui.x1.setEnabled(false);
                            gui.x1.setText("N/A");
                        }
                    }
                }
            });
        });
        
        // "Find Root" button
        gui.findRootBtn.addActionListener(e -> {
            // Use while true to check for input values
            while(true) {
                
                // Clear the table
                gui.initializeTable();
                
                // Input values to be checked
                double x0;
                double x1;
                double precision;
                
                // Check if any numerical method is selected
                if(gui.getSelectedMethods().length == 0) {
                    gui.warning("Please select 1 or more numerical methods");
                    break;
                }
                
                // Check x0
                try {
                    x0 = Double.parseDouble(gui.x0.getText());
                } catch(NumberFormatException nfe) {
                    gui.warning("Ensure x0 is a valid number");
                    break;
                }
                
                // Check x1 (if enabled)
                try {
                    if(gui.x1.isEnabled()) {
                        x1 = Double.parseDouble(gui.x1.getText());
                    } else {
                        x1 = x0;
                    }
                } catch(NumberFormatException nfe) {
                    gui.warning("Ensure x1 is a valid number");
                    break;
                }
                
                // Check precision
                try {
                    precision = Double.parseDouble(gui.precision.getText());
                } catch(NumberFormatException nfe) {
                    gui.warning("Ensure precision is a valid number");
                    break;
                }
                
                // ALL CHECKS PASSED
                // Now process each numerical method
                for(String method: gui.getSelectedMethods()) {
                    switch(method) {
                        case "newtonRaphson":
                            // Newton Raphson implementation
                            gui.addTableRow(new String[] {"Newton Raphson", "method"});
                            LinkedList nr = newtonRaphson(currentFunction, x0, precision);
                            // Convert LinkedList to array so I can iterate
                            double[] nr_array = nr.toDoubleArray();
                            
                            // Add values to table
                            for (int i = 0; i < nr_array.length; i++) {
                                gui.addTableRow(new String[] { Integer.toString(i), String.format("%.10f", nr_array[i]) });
                            }
                            break;
                        case "secant":
                            // Secant method implementatin
                            gui.addTableRow(new String[] {"Secant", "method"});
                            double[] secant = secant(currentFunction, x0, x1, precision);
                            for (int i = 0; i < secant.length; i++) {
                                gui.addTableRow(new String[] { Integer.toString(i), String.format("%.10f", secant[i]) });
                            }
                            break;
                        case "bisection":
                            // Try/catch because bisection requires f(x0) and f(x1) to be of opposite sign.
                            try {
                                // Bisection method implementatin
                                double[] bisection = bisection(currentFunction, x0, x1, precision);
                                gui.addTableRow(new String[] {"Bisection", "method"});
                                for (int i = 0; i < bisection.length; i++) {
                                    gui.addTableRow(new String[] { Integer.toString(i), String.format("%.10f", bisection[i]) });
                                }
                            } catch(Exception ex) {
                                gui.addTableRow(new String[] {"Failed:", "Bisection method"});
                                gui.warning(ex.getMessage());
                            }
                            break;
                        case "other":
                            gui.warning("Not yet implemented.");
                            break;
                    }
                }
                // Switch tab to display the result to the user.
                gui.switchTab();
                
                break;
            }
        });
    }
    
    /**
     * Implementation of the Newton-Raphson method to find the root.
     * 
     * @param f Function whose roots need to be found
     * @param x0 Starting point
     * @param precision Degree of accuracy for this method to stop
     * 
     * @return LinkedList containing the iterations of the x0 value.
     */
    public LinkedList newtonRaphson(Function f, double x0, double precision) {
        // Function must use LinkedList
        f.useArray(false);
        
        LinkedList out = new LinkedList();
        out.add(x0);
        while(true) {
            double Xn = out.getLastElement();
            // Newton-Raphson formula
            double Xnplus1 = Xn - (f.computeY(Xn) / f.computeYderivative(Xn));
            out.add(Xnplus1);
            if(Math.abs(Xn - Xnplus1) <= precision) break;
        }
        return out;
    }
    
    /**
     * Implementation of the Secant method to find the root.
     * 
     * @param f Function whose roots need to be found
     * @param x0 First starting point
     * @param x1  Second starting point
     * @param precision Degree of accuracy for this method to stop
     * 
     * @return LinkedList containing the iterations of the x0 value.
     */
    public double[] secant(Function f, double x0, double x1, double precision) {
        // Function must use Array
        f.useArray(true);
        
        double[] out = {x0, x1};
        while(true) {
            double Xn_1 = out[out.length-1];
            double Xn_2 = out[out.length-2];
            // Secant method formula
            double Xn = Xn_1 - f.computeY(Xn_1) * (Xn_1 - Xn_2) / (f.computeY(Xn_1) - f.computeY(Xn_2));
            double[] out_copy = out.clone();
            out = new double[out.length + 1];
            System.arraycopy(out_copy, 0, out, 0, out_copy.length);
            out[out.length - 1] = Xn;
            if(Math.abs(Xn - Xn_1) < precision) break;
        }
        return out;
    }
    
    /**
     * Implementation of the Bisection method to find the root.
     * 
     * @param f Function whose roots need to be found
     * @param x0 First starting point
     * @param x1  Second starting point
     * @param precision Degree of accuracy for this method to stop
     * 
     * @return LinkedList containing the iterations of the x0 value.
     * @throws java.lang.Exception Bisection requires f(x0) and f(x1) to be of opposite sign. Throws error if this condition is not fulfilled.
     */
    public double[] bisection(Function f, double x0, double x1, double precision) throws Exception{
        // Function must use Array
        f.useArray(true);
        
        double[] out = {x0, x1};
        
        double a = out[out.length-1];
        double b = out[out.length-2];
        
        // If f(a) and f(b) are of opposite sign, carry on with the bisection method, else throw exception
        if((f.computeY(a) > 0 && f.computeY(b) < 0) || (f.computeY(a) < 0 && f.computeY(b) > 0)) {        
            while(true) {
                double c = (a + b) / 2;

                double Fa = f.computeY(a);
                double Fc = f.computeY(c);

                double[] out_copy = out.clone();
                out = new double[out.length + 1];
                System.arraycopy(out_copy, 0, out, 0, out_copy.length);
                out[out.length - 1] = c;

                if((Fc < 0 && Fa > 0) || (Fc > 0 && Fa < 0)) {
                    b = c;
                    if(Math.abs(c - a) < precision) break;
                } else {
                    a = c;
                    if(Math.abs(c - b) < precision) break;
                }
            }
            return out;
        } else {
            throw new Exception("f(x0) and f(x1) must be of opposite sign");
        }
    }
}
