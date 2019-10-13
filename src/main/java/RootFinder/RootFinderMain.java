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
    
    public static void main(String[] args) {
        RootFinderMain rootFinder = new RootFinderMain();
    }
    
    public RootFinderMain() {
        // Set up GUI
        RootFinderGUI gui = new RootFinderGUI(functionsAvailableLabels, new Quadratic(false));
        
        /**
         * ActionListeners
         */
        
        // Update the chart when user changes function using dropdown menu
        gui.functionsDropdown.addActionListener(e -> {
            switch (functionsAvailableTags[gui.functionsDropdown.getSelectedIndex()]) {
                case "quadratic":
                    gui.updateChart(new Quadratic(true));
                    break;
                case "logarithm":
                    gui.updateChart(new Logarithm(true));
                    break;
                case "euler":
                    gui.updateChart(new Euler(true));
                    break;
            }
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
            LinkedList nr = newtonRaphson(new Quadratic(false), Double.parseDouble(gui.x0.getText()), Double.parseDouble(gui.precision.getText()));
            // Convert LinkedList to array so I can iterate
            double[] nr_array = nr.toDoubleArray();
            
            // Add values to table
            for (int i = 0; i < nr_array.length; i++) {
                gui.addTableRow(new String[] { Integer.toString(i), String.format("%.10f", nr_array[i]) });
            }
            
            gui.switchTab();
        });
    }
    
    public LinkedList newtonRaphson(Function f, double startPoint, double precision) {
        LinkedList out = new LinkedList();
        out.add(startPoint);
        while(true) {
            double lastX = out.getLastElement();
            double nextX = lastX - (f.computeY(lastX) / f.computeYderivative(lastX));
            if(lastX - nextX <= precision) break;
            out.add(nextX);
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
