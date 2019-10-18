/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RootFinder;

import RootFinder.Functions.Function;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BoxLayout;
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
    private XChartPanel<XYChart> chartPanel; // Chart panel
    
    // User inputs
    public JTextField x0 = new JTextField("", 4);
    public JTextField x1 = new JTextField("", 4);
    public JTextField precision = new JTextField("", 4);
    public JButton findRootBtn = new JButton("Find Root");
    
    public RootFinderGUI(String[] functionsAvailableLabels, Function function){
        this.functionsAvailableLabels = functionsAvailableLabels;
        this.function = function;
        this.chart = QuickChart.getChart("Function", "x", "y", "f(x)", function.getX(), function.getY());
        initializeGUI();
    }

    private void initializeGUI() {        
        setTitle("Root Finder");
        setSize(1200, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        
        // Split pane
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel(), rightPanel());
        sp.setOneTouchExpandable(true);
        
        add(sp);
        
        setVisible(true);
    }
    
    private JComponent leftPanel() {
        leftPanel.addTab("Input", userInputPanel());
        leftPanel.addTab("Table", tabularData());
        
        return leftPanel;
    }
    
    private JComponent userInputPanel() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        
        pnl.add(new JLabel("Select a function:"));
        functionsDropdown = new JComboBox<>(functionsAvailableLabels);
        functionsDropdown.setActionCommand("functionSelected");
        functionsDropdown.setMaximumSize(new Dimension(200, 25));
        functionsDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnl.add(functionsDropdown);
        
        pnl.add(new JLabel("Select numerical method(s):"));
        numericalMethodsBtnGroup = new ArrayList<>();
        pnl.add(checkBox("Newton-Raphson", "newtonRaphson", numericalMethodsBtnGroup));
        pnl.add(checkBox("Secant", "secant", numericalMethodsBtnGroup));
        pnl.add(checkBox("Bisection", "bisection", numericalMethodsBtnGroup));
        pnl.add(checkBox("Other", "other", numericalMethodsBtnGroup));
        
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
        
        JPanel precitionInputPanel = new JPanel();
        precitionInputPanel.setLayout(new BoxLayout(precitionInputPanel, BoxLayout.X_AXIS));
        precitionInputPanel.setMaximumSize(new Dimension(250, precision.getPreferredSize().height));
        precitionInputPanel.add(new JLabel("Enter precision: "));
        precitionInputPanel.add(precision);
        precitionInputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnl.add(precitionInputPanel);
        
        pnl.add(findRootBtn);
        
        return pnl;
    }
    
    private JCheckBox checkBox(String text, String actionCommand, ArrayList<JCheckBox> btnGroup) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setActionCommand(actionCommand);
        btnGroup.add(checkBox);
        return checkBox;
    }
    
    private JComponent tabularData() {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        
        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(container.getSize());
        
        // Add some dummy data
        tableData.addColumn("Iteration (i)");
        tableData.addColumn("Xi");
        initializeTable();
        
        container.add(sp, BorderLayout.CENTER);
        
        return container;
    }
    
    public void initializeTable() {
        tableData.setRowCount(0);
    }
    
    private JComponent rightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        
        chartPanel = new XChartPanel<>(chart);
        
        // Enable zooming in into the graph
        SelectionZoom sz = new SelectionZoom();
        sz.init(chartPanel);
        
        rightPanel.add(chartPanel, BorderLayout.CENTER);
        
        return rightPanel;
    }
    
    public void updateChart(Function f) {
        function = f;
        chart.updateXYSeries("f(x)", function.getX(), function.getY(), null);
        chartPanel.repaint();
    }
    
    public void addTableRow(String[] data) {
        tableData.addRow(data);
    }
    
    public void switchTab() {
        leftPanel.setSelectedIndex( leftPanel.getSelectedIndex() == 0 ? 1 : 0 );
    }
    
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
    
    public void warning(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
}
