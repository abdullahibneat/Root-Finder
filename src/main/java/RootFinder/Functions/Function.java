package RootFinder.Functions;

import LinkedList.LinkedList;

/**
 * Abstract class for f(x) functions
 *
 * @author Abdullah
 */
public abstract class Function {
    
    // x, y values to store as array
    private double[] x_array;
    private double[] y_array;
    
    // x, y values to store as LinkedList
    private LinkedList x_list;
    private LinkedList y_list;
    
    // Length of arrays / LinkedLists
    private final int length;
    
    // Use array or LinkedList?
    private boolean useArray;
    
    // Offset x values to center the graph
    private final double offset;
    
    // Increase the precision of the plot, making the graph more smooth
    private final int precision;
    
    /**
     * Constructor to initialise a function
     * 
     * @param useArray Set to true to use Array, false for LinkedList
     * @param length Number of x values, larger will generate a more accurate graph
     * @param offset Offset x values to center the graph
     * @param precision Makes the graph smoother
     */
    public Function(boolean useArray, int length, double offset, int precision) {
        this.offset = offset;
        this.useArray = useArray;
        this.precision = precision;
        this.length = length;
        useArray(useArray);
    }
    
    /**
     * Method to populate the Arrays / LinkedLists
     * 
     * @param length Number of x values, larger will generate a more accurate graph
     */
    private void populateValues() {
        
        int index = 0;
        double step = (double)1 / precision;
        
        for (double i = 0; i < length; i+=step) {
            double x_value = i + offset;
            if(useArray) {
                x_array[index] = x_value;
                y_array[index++] = computeY(x_value);
            } else {
                x_list.add(x_value);
                y_list.add(computeY(x_value));
            }
        }
    }
    
    /**
     * Method to switch between using array and LinkedList
     * 
     * @param b Set to true to use Array, false for LinkedList
     */
    public final void useArray(boolean b) {
        useArray = b;
        
        if(useArray) {
            x_array = new double[length*precision];
            y_array = new double[length*precision];
            
            x_list = null;
            y_list = null;
        } else {
            x_list = new LinkedList();
            y_list = new LinkedList();
            
            x_array = null;
            y_array = null;
        }
        
        populateValues();
    }
    
    /**
     * Abstract method
     * This is the f(x) function.
     * 
     * @param x The x value
     * @return y value
     */
    public abstract double computeY(double x);
    
    /**
     * Abstract method
     * This is the f'(x) function.
     * 
     * @param x The x value
     * @return derivative at x
     */
    public abstract double computeYderivative(double x);
    
    /**
     * @return Array of all x values
     */
    public double[] getX() {
        if(useArray) {
            return x_array;
        } else {
            return x_list.toDoubleArray();
        }
    }
    
    /**
     * @return Array of y values
     */
    public double[] getY() {
        if(useArray) {
            return y_array;
        } else {
            return y_list.toDoubleArray();
        }
    }
}
