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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
public class RootFinderGUI extends JFrame implements ActionListener {
    
    public JComboBox<String> functionsDropdown; // Dropdown of functions
    private final String[] functionsAvailableLabels; // Functions in JComboBox
    private ArrayList<JCheckBox> numericalMethodsBtnGroup; // List of checkboxes for numerical methods
    
    private final DefaultTableModel tableData = new DefaultTableModel();
    private final JTable table = new JTable(tableData);
    
    private Function function;
    private final XYChart chart; // Function plot
    private XChartPanel<XYChart> chartPanel; // Chart panel
    
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
        
        add(leftPanel());        
        add(rightPanel());
        
        setVisible(true);
    }
    
    private JComponent leftPanel() {
        JTabbedPane leftPanel = new JTabbedPane(JTabbedPane.TOP);
        leftPanel.addTab("Input", userInputPanel());
        leftPanel.addTab("Table", tabularData());
        
        return leftPanel;
    }
    
    private JComponent userInputPanel() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        
        pnl.add(new JLabel("Select a function:"));
        functionsDropdown = new JComboBox<>(functionsAvailableLabels);
        functionsDropdown.addActionListener(this);
        functionsDropdown.setActionCommand("functionSelected");
        functionsDropdown.setMaximumSize(new Dimension(200, 25));
        functionsDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnl.add(functionsDropdown);
        
        pnl.add(new JLabel("Select numerical method(s):"));
        numericalMethodsBtnGroup = new ArrayList<>();
        pnl.add(checkBox("Newton-Raphson", "numericalMethodSelected", numericalMethodsBtnGroup));
        pnl.add(checkBox("Secant", "numericalMethodSelected", numericalMethodsBtnGroup));
        pnl.add(checkBox("Bisection", "numericalMethodSelected", numericalMethodsBtnGroup));
        pnl.add(checkBox("Other", "numericalMethodSelected", numericalMethodsBtnGroup));
        
        JPanel startingPointInputPanel = new JPanel();
        startingPointInputPanel.setLayout(new BoxLayout(startingPointInputPanel, BoxLayout.X_AXIS));
        JTextField x0 = new JTextField("", 4);
        startingPointInputPanel.setMaximumSize(new Dimension(250, x0.getPreferredSize().height));
        JTextField x1 = new JTextField("", 4);
        startingPointInputPanel.add(new JLabel("Set starting point(s): "));
        startingPointInputPanel.add(new JLabel("x0: "));
        startingPointInputPanel.add(x0);
        startingPointInputPanel.add(new JLabel("x1: "));
        startingPointInputPanel.add(x1);
        startingPointInputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnl.add(startingPointInputPanel);
        
        JButton findRootBtn = new JButton("Find Root");
        findRootBtn.addActionListener(this);
        findRootBtn.setActionCommand("FindRoot");
        pnl.add(findRootBtn);
        
        return pnl;
    }
    
    private JCheckBox checkBox(String text, String actionCommand, ArrayList<JCheckBox> btnGroup) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.addActionListener(this);
        checkBox.setActionCommand(actionCommand);
        btnGroup.add(checkBox);
        return checkBox;
    }
    
    private JComponent tabularData() {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        
        // Add some dummy data
        tableData.addColumn("Column 1");
        tableData.addColumn("Column 2");
        
        tableData.addRow(new Object[] {"x0", "x1"});
        
        JButton addRowBtn = new JButton("Add row");
        addRowBtn.addActionListener(this);
        addRowBtn.setActionCommand("AddRow");
        
        container.add(table, BorderLayout.CENTER);
        container.add(addRowBtn, BorderLayout.SOUTH);
        
        return container;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "numericalMethodSelected":
                System.out.println("------------------\nnumericalMethodsSelected\n------------------");
                for(JCheckBox c: numericalMethodsBtnGroup) {
                    String isSelected = c.isSelected() ? " is " : " is NOT ";
                    System.out.println(c.getText() + isSelected + "selected");
                }
                System.out.println("------------------");
                break;
            case "AddRow":
                tableData.addRow(new Object[] {"new", "data"});
                break;
            default:
                System.out.println(e.getActionCommand());
                break;
        }
    }
    
}
