package RootFinder;

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
            // Testing if addTableRow works
            gui.addTableRow(new String[] {gui.x0.getText() + ", " + gui.x1.getText(), gui.precision.getText()});
            gui.switchTab();
        });
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
