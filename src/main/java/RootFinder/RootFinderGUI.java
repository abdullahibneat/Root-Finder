package RootFinder;

import RootFinder.Functions.Function;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.internal.chartpart.SelectionZoom;

/**
 * The User Interface for the main application.
 *
 * @author Abdullah
 */
public class RootFinderGUI extends JFrame {
    
    public JComboBox<String> functionsDropdown; // Dropdown of functions
    private final String[] functionsAvailableLabels; // Functions in JComboBox
    public ArrayList<JCheckBox> numericalMethodsBtnGroup; // List of checkboxes for numerical methods
    public int requireX1 = 0; // This is to check if x1 input should be enabled or not
    
    private final JTabbedPane leftPanel = new JTabbedPane(JTabbedPane.TOP); // Tabbed panel
    private final DefaultTableModel tableData = new DefaultTableModel();
    private final JTable table = new JTable(tableData);
    
    private Function function;
    private final XYChart chart; // Function plot
    private final XChartPanel<XYChart> chartPanel; // Chart panel
    
    // User inputs
    public JTextField x0 = new JTextField("", 4);
    public JTextField x1 = new JTextField("", 4);
    public JTextField precision = new JTextField("", 4);
    public JButton findRootBtn = new JButton("Find Root");
    
    /**
     * Constructor for the GUI
     * 
     * @param functionsAvailableLabels The labels for each function that is available
     * @param function The function that is displayed by default
     */
    public RootFinderGUI(String[] functionsAvailableLabels, Function function){
        this.functionsAvailableLabels = functionsAvailableLabels;
        this.function = function;
        this.chart = QuickChart.getChart("Function", "x", "y", "f(x)", function.getX(), function.getY());
        
        // Add the chart to its panel
        chartPanel = new XChartPanel<>(chart);
        
        initializeGUI();
    }

    /**
     * Method to initialise the GUI
     */
    private void initializeGUI() {        
        setTitle("Root Finder");
        setSize(1200, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        
        // Split pane
        // Allows user to resize/hide panels for easier reading, e.g. make table larger, or hide the table altogether.
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel(), rightPanel());
        sp.setOneTouchExpandable(true);
        
        add(sp);
        
        setVisible(true);
    }
    
    /**
     * Method to initialise the left panel.
     * 
     * The left panel is a JTabbedPane, which has tabs for user input and the table with all x values.
     * 
     * @return JTabbedPane
     */
    private JComponent leftPanel() {
        leftPanel.addTab("Input", userInputPanel());
        leftPanel.addTab("Table", tabularData());
        
        return leftPanel;
    }
    
    /**
     * JPanel for user input
     * 
     * @return JPanel with input fields
     */
    private JComponent userInputPanel() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        
        // Function selection dropdown
        pnl.add(new JLabel("Select a function:"));
        functionsDropdown = new JComboBox<>(functionsAvailableLabels);
        functionsDropdown.setActionCommand("functionSelected");
        functionsDropdown.setMaximumSize(new Dimension(200, 25));
        functionsDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnl.add(functionsDropdown);
        
        // Numerical Method selection
        pnl.add(new JLabel("Select numerical method(s):"));
        numericalMethodsBtnGroup = new ArrayList<>();
        pnl.add(checkBox("Newton-Raphson", "newtonRaphson", numericalMethodsBtnGroup));
        pnl.add(checkBox("Secant", "secant", numericalMethodsBtnGroup));
        pnl.add(checkBox("Bisection", "bisection", numericalMethodsBtnGroup));
        pnl.add(checkBox("False Position", "falsePosition", numericalMethodsBtnGroup));
        
        // Starting points fields
        JPanel startingPointInputPanel = new JPanel();
        startingPointInputPanel.setLayout(new BoxLayout(startingPointInputPanel, BoxLayout.X_AXIS));
        startingPointInputPanel.setMaximumSize(new Dimension(250, x0.getPreferredSize().height));
        startingPointInputPanel.add(new JLabel("Set starting point(s): "));
        startingPointInputPanel.add(new JLabel("x0: "));
        startingPointInputPanel.add(x0);
        startingPointInputPanel.add(new JLabel("x1: "));
        
        // Disable x1 input by default
        x1.setEnabled(false);
        x1.setText("N/A");
        
        startingPointInputPanel.add(x1);
        startingPointInputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnl.add(startingPointInputPanel);
        
        // Precision field
        JPanel precitionInputPanel = new JPanel();
        precitionInputPanel.setLayout(new BoxLayout(precitionInputPanel, BoxLayout.X_AXIS));
        precitionInputPanel.setMaximumSize(new Dimension(250, precision.getPreferredSize().height));
        precitionInputPanel.add(new JLabel("Enter precision: "));
        precitionInputPanel.add(precision);
        precitionInputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnl.add(precitionInputPanel);
        
        // Add the button
        pnl.add(findRootBtn);
        
        return pnl;
    }
    
    /**
     * Method to generate a JCheckBox
     * 
     * @param text Text to be displayed
     * @param actionCommand Action Command
     * @param btnGroup Button Group the JCheckBox should be added to
     * 
     * @return JCheckBox
     */
    private JCheckBox checkBox(String text, String actionCommand, ArrayList<JCheckBox> btnGroup) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setActionCommand(actionCommand);
        btnGroup.add(checkBox);
        return checkBox;
    }
    
    /**
     * JPanel with a JTable component
     * 
     * This is used to display all the iterations of each numerical method
     * 
     * @return Table
     */
    private JComponent tabularData() {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        
        // Allow user to select text from table cell
        JTextField tf = new JTextField();
        tf.setEditable(false);
        DefaultCellEditor editor = new DefaultCellEditor(tf);
        table.setDefaultEditor(Object.class, editor);
        
        // If the table has many rows, show a vertical scrollbar
        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(container.getSize());
        
        // Set column names
        tableData.addColumn("i");
        tableData.addColumn("x");
        tableData.addColumn("F(x)");
        // Set the first column to be of fixed witdth
        table.getColumnModel().getColumn(0).setMaxWidth(25);
        
        initializeTable();
        
        container.add(sp, BorderLayout.CENTER);
        
        return container;
    }
    
    /**
     * Method to empty the table
     */
    public void initializeTable() {
        tableData.setRowCount(0);  
        removeRootsFromGraph();
        chartPanel.repaint();
    }
    
    /**
     * Method to remove any roots displayed on the chart
     */
    public void removeRootsFromGraph() {
        String toRemove = "";
        for(String s: chart.getSeriesMap().keySet()) {
            if(!s.equals("f(x)")) toRemove += s + ",";
        }
        // Using a separate for loop to avoid java.util.concurrentmodificationexception
        for(String s: toRemove.split(",")) {
            chart.removeSeries(s);
        }
    }
    
    /**
     * Method to initialise the right panel.it
     * 
     * The right panel contains the XChart plot to display the function to the user.
     * 
     * @return XChart panel
     */
    private JComponent rightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        
        // Enable legend
        chart.getStyler().setLegendVisible(true);
        
        // Enable zooming in into the graph
        SelectionZoom sz = new SelectionZoom();
        sz.init(chartPanel);
        
        rightPanel.add(chartPanel, BorderLayout.CENTER);
        
        return rightPanel;
    }
    
    /**
     * Method to update the function displayed in the chart
     * 
     * @param f New function to be displayed
     */
    public void updateChart(Function f) {
        function = f;
        chart.updateXYSeries("f(x)", function.getX(), function.getY(), null);
        chartPanel.repaint();
    }
    
    /**
     * Method to add a new row to the table.
     * 
     * @param data The data to be added to the row
     */
    public void addTableRow(String[] data) {
        tableData.addRow(data);
    }
    
    /**
     * Method to switch tab in the JTabbedPane
     */
    public void switchTab() {
        leftPanel.setSelectedIndex( leftPanel.getSelectedIndex() == 0 ? 1 : 0 );
    }
    
    /**
     * Method to retrieve the numerical methods the user has selected
     * 
     * @return Array of actionCommands of selected JCheckBoxes
     */
    public String[] getSelectedMethods() {
        String[] out = new String[0];
        for(JCheckBox c: numericalMethodsBtnGroup) {
            if(c.isSelected()) {
                String[] out_copy = out.clone();
                out = new String[out.length + 1];
                System.arraycopy(out_copy, 0, out, 0, out_copy.length);
                out[out.length - 1] = c.getActionCommand();
            }
        }
        
        return out;
    }
    
    /**
     * Method to display a warning to the user
     * 
     * @param message Message to be displayed
     */
    public void warning(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    /**
     * Method to add a single point to the chart given its x and y coordinate.
     * 
     * @param name Name of the series
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void addSeries(String name, double x, double y) {
        if(!chart.getSeriesMap().keySet().contains(name)) {
            chart.addSeries(name, new double[] {x}, new double[] {y});
            chartPanel.repaint();
        }
    }
    
}
