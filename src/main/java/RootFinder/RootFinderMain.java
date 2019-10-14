package RootFinder;

import LinkedList.LinkedList;
import RootFinder.Functions.Function;
import RootFinder.Functions.Euler;
import RootFinder.Functions.Logarithm;
import RootFinder.Functions.Quadratic;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 * Main class
 *
 * @author Abdullah
 */
public class RootFinderMain {
    
    private final String[] functionsAvailableLabels = {"f(x) = x - x^2", "f(x) = ln(x+1) + 1", "f(x) = e^x - 3x"}; // Functions in JComboBox
    private final String[] functionsAvailableTags = {"quadratic", "logarithm", "euler"}; // Map above functions to recognizable tag
    
    private String selectedFunction; // Value of dropdown menu
    private Function currentFunction = new Quadratic(false);
    
    public static void main(String[] args) {
        RootFinderMain rootFinder = new RootFinderMain();
    }
    
    public RootFinderMain() {
        // Set up GUI
        RootFinderGUI gui = new RootFinderGUI(functionsAvailableLabels, currentFunction);
        
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
        gui.numericalMethodsBtnGroup.forEach(checkbox -> {
            checkbox.addActionListener(e -> {
                String status = checkbox.isSelected() ? " checked" : " unchecked";
                System.out.println(e.getActionCommand() + status);
            });
        });
        
        // "Find Root" button
        gui.findRootBtn.addActionListener(e -> {
            // Clear the table
            gui.initializeTable();
            
            // Newton Raphson implementation
            LinkedList nr = newtonRaphson(currentFunction, Double.parseDouble(gui.x0.getText()), Double.parseDouble(gui.precision.getText()));
            // Convert LinkedList to array so I can iterate
            double[] nr_array = nr.toDoubleArray();
            
            // Add values to table
            for (int i = 0; i < nr_array.length; i++) {
                gui.addTableRow(new String[] { Integer.toString(i), String.format("%.10f", nr_array[i]) });
            }
            
            // Secant method implementatin
            gui.addTableRow(new String[] {"Secant", "method"});
            double[] secant = secant(currentFunction, Double.parseDouble(gui.x0.getText()), Double.parseDouble(gui.x1.getText()), Double.parseDouble(gui.precision.getText()));
            for (int i = 0; i < secant.length; i++) {
                gui.addTableRow(new String[] { Integer.toString(i), String.format("%.10f", secant[i]) });
            }
            
            gui.switchTab();
        });
    }
    
    public LinkedList newtonRaphson(Function f, double x0, double precision) {
        // Function must use LinkedList
        f.useArray(false);
        
        LinkedList out = new LinkedList();
        out.add(x0);
        while(true) {
            double Xn = out.getLastElement();
            double Xnplus1 = Xn - (f.computeY(Xn) / f.computeYderivative(Xn));
            out.add(Xnplus1);
            if(Math.abs(Xn - Xnplus1) <= precision) break;
        }
        return out;
    }
    
    public double[] secant(Function f, double x0, double x1, double precision) {
        // Function must use Array
        f.useArray(true);
        
        double[] out = {x0, x1};
        while(true) {
            double Xn_1 = out[out.length-1];
            double Xn_2 = out[out.length-2];
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
     * Sample charts to test the XChart library
     */
    private void demoCharts() {
        XYChart demoChart = new XYChartBuilder().width(600).height(500).build();
        
        Quadratic quadratic = new Quadratic(true);
        Logarithm logarithm = new Logarithm(true);
        Euler euler = new Euler(true);
        
        demoChart.addSeries("Quadratic", quadratic.getX(), quadratic.getY()).setMarker(SeriesMarkers.NONE);
        demoChart.addSeries("Logarithm", logarithm.getX(), logarithm.getY()).setMarker(SeriesMarkers.NONE);
        demoChart.addSeries("Euler", euler.getX(), euler.getY()).setMarker(SeriesMarkers.NONE);
        new SwingWrapper<>(demoChart).displayChart();
    }
}
