package RootFinder;

import LinkedList.LinkedList;
import NumericalMethods.NumericalMethods;
import RootFinder.Functions.Function;
import RootFinder.Functions.Euler;
import RootFinder.Functions.Logarithm;
import RootFinder.Functions.Quadratic;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    
    // Numerical methods
    private final NumericalMethods numericalMethods = new NumericalMethods();
    
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
            // Remove any displayed roots from previous numerical methods
            gui.removeRootsFromGraph();
            
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
                 * check if bisection, secant and/or false position are still selected.
                 * If they are, the x1 input is still required,
                 * otherwise x1 can be disabled.
                 */
                if(!checkbox.getActionCommand().equals("newtonRaphson")) {
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
            runRootFinder();
        });
        
        // KeyListener listener for input fields
        KeyListener enterKeyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) runRootFinder();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        
        gui.x0.addKeyListener(enterKeyListener);
        gui.x1.addKeyListener(enterKeyListener);
        gui.precision.addKeyListener(enterKeyListener);
    }
    
    /**
     * Method to run the numerical methods.
     */
    private void runRootFinder() {
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
                // Precision must be between 0 and 1 (e.g. 0.001)
                if(!(precision > 0 && precision < 1)) {
                    gui.warning("Precision must be between 0 and 1");
                    break;
                }
            } catch(NumberFormatException nfe) {
                gui.warning("Ensure precision is a valid number");
                break;
            }

            // ALL CHECKS PASSED
            // Now process each numerical method
            for(String method: gui.getSelectedMethods()) {
                switch(method) {
                    case "newtonRaphson":
                        try {
                            // Newton Raphson implementation
                            gui.addTableRow(new String[] {"Newton Raphson", "method"});
                            LinkedList nr = numericalMethods.newtonRaphson(currentFunction, x0, precision);
                            // Convert LinkedList to array so I can iterate
                            double[] nr_array = nr.toDoubleArray();

                            // Add values to table
                            for (int i = 0; i < nr_array.length; i++) {
                                gui.addTableRow(new String[] { Integer.toString(i), String.format("%.10f", nr_array[i]) });
                            }
                            // Show the last root on the graph
                            gui.addSeries("Newton-Raphson", nr.getLastElement(), currentFunction.computeY(nr.getLastElement()));
                        } catch(ArithmeticException ex) {
                            gui.addTableRow(new String[] {"Failed:", "Newton-Raphson method"});
                            gui.warning(ex.getMessage());
                        }
                        break;
                    case "secant":
                        try {
                            // Secant method implementatin
                            gui.addTableRow(new String[] {"Secant", "method"});
                            double[] secant = numericalMethods.secant(currentFunction, x0, x1, precision);
                            for (int i = 0; i < secant.length; i++) {
                                gui.addTableRow(new String[] { Integer.toString(i), String.format("%.10f", secant[i]) });
                            }
                            // Show the last root on the graph
                            gui.addSeries("Secant", secant[secant.length - 1], currentFunction.computeY(secant[secant.length - 1]));
                        } catch(ArithmeticException ex) {
                            gui.addTableRow(new String[] {"Failed:", "Secant method"});
                            gui.warning(ex.getMessage());
                        }
                        break;
                    case "bisection":
                        // Try/catch because bisection requires f(x0) and f(x1) to be of opposite sign.
                        try {
                            // Bisection method implementatin
                            double[] bisection = numericalMethods.bisection(currentFunction, x0, x1, precision);
                            gui.addTableRow(new String[] {"Bisection", "method"});
                            for (int i = 0; i < bisection.length; i++) {
                                gui.addTableRow(new String[] { Integer.toString(i), String.format("%.10f", bisection[i]) });
                            }
                            // Show the last root on the graph
                            gui.addSeries("Bisection", bisection[bisection.length - 1], currentFunction.computeY(bisection[bisection.length - 1]));
                        } catch(ArithmeticException ex) {
                            gui.addTableRow(new String[] {"Failed:", "Bisection method"});
                            gui.warning(ex.getMessage());
                        }
                        break;
                    case "falsePosition":
                        // Try/catch because false position requires f(x0) and f(x1) to be of opposite sign.
                        try {
                            // Bisection method implementatin
                            LinkedList falsePosition = numericalMethods.falsePosition(currentFunction, x0, x1, precision);                                
                            // Convert LinkedList to array so I can iterate
                            double[] fs_array = falsePosition.toDoubleArray();
                            gui.addTableRow(new String[] {"False Position", "method"});
                            for (int i = 0; i < fs_array.length; i++) {
                                gui.addTableRow(new String[] { Integer.toString(i), String.format("%.10f", fs_array[i]) });
                            }
                            // Show the last root on the graph
                            gui.addSeries("False Position", fs_array[fs_array.length - 1], currentFunction.computeY(fs_array[fs_array.length - 1]));
                        } catch(ArithmeticException ex) {
                            gui.addTableRow(new String[] {"Failed:", "False position method"});
                            gui.warning(ex.getMessage());
                        }
                        break;
                }
            }
            // Switch tab to display the result to the user.
            gui.switchTab();

            break;
        }
    }
}
