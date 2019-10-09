package RootFinder;

import RootFinder.Functions.Euler;
import RootFinder.Functions.Logarithm;
import RootFinder.Functions.Quadratic;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 * Main class
 *
 * @author Abdullah
 */
public class RootFinder extends JFrame implements ActionListener {
    
    private JComboBox<String> functionsDropdown; // Dropdown of functions
    private final String[] functionsAvailableLabels = {"f(x) = x - x^2", "f(x) = ln(x+1) + 1", "f(x) = e^x - 3x"}; // Functions in JComboBox
    private final String[] functionsAvailableTags = {"quadratic", "logarithm", "euler"}; // Map above functions to recognizable tag
    private ArrayList<JCheckBox> numericalMethodsBtnGroup; // List of checkboxes for numerical methods
    private XYChart chart; // Function plot
    private XChartPanel<XYChart> chartPanel; // Chart panel
    
    private final DefaultTableModel tableData = new DefaultTableModel();
    private JTable table = new JTable(tableData);
    
    public static void main(String[] args) {
        RootFinder rootFinder = new RootFinder();
        rootFinder.setVisible(true);
    }
    
    public RootFinder() {
        view();
    }

    private void view() {
        setTitle("Root Finder");
        setSize(1200, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        
        add(leftPanel());        
        add(rightPanel());
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
    
    private JRadioButton radioButton(String text, String actionCommand, ButtonGroup btnGroup) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setActionCommand(actionCommand);
        btnGroup.add(radioButton);
        
        return radioButton;
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
        
        Quadratic quadratic = new Quadratic(true);
        chart = QuickChart.getChart("Function", "X", "Y", "y(x)", quadratic.getX(), quadratic.getY());
        chartPanel = new XChartPanel<>(chart);
        rightPanel.add(chartPanel, BorderLayout.CENTER);
        
        return rightPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "functionSelected":
                switch (functionsAvailableTags[functionsDropdown.getSelectedIndex()]) {
                    case "quadratic":
                        Quadratic quadratic = new Quadratic(true);
                        chart.updateXYSeries("y(x)", quadratic.getX(), quadratic.getY(), null);
                        chartPanel.repaint();
                        break;
                    case "logarithm":
                        Logarithm logarithm = new Logarithm(true);
                        chart.updateXYSeries("y(x)", logarithm.getX(), logarithm.getY(), null);
                        chartPanel.repaint();
                        break;
                    case "euler":
                        Euler euler = new Euler(true);
                        chart.updateXYSeries("y(x)", euler.getX(), euler.getY(), null);
                        chartPanel.repaint();
                        break;
                }
                break;
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
