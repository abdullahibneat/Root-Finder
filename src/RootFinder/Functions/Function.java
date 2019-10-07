package RootFinder.Functions;

import LinkedList.LinkedList;

/**
 * Abstract class for f(x) functions
 *
 * @author Abdullah
 */
abstract class Function {
    
    // x, y values to store as array
    private final double[] x_array;
    private final double[] y_array;
    
    // x, y values to store as LinkedList
    private final LinkedList x_list;
    private final LinkedList y_list;
    
    // Use array or LinkedList?
    private final boolean useArray;
    
    // Offset x values to center the graph
    private final double offset;
    
    /**
     * Constructor to initialise a function
     * 
     * @param useArray Set to true to use Array, false for LinkedList
     * @param length Number of x values, larger will generate a more accurate graph
     * @param offset Offset x values to center the graph
     */
    public Function(boolean useArray, int length, double offset) {
        this.offset = offset;
        this.useArray = useArray;
        
        if(useArray) {
            x_array = new double[length];
            y_array = new double[length];
            
            x_list = null;
            y_list = null;
        } else {
            x_list = new LinkedList();
            y_list = new LinkedList();
            
            x_array = null;
            y_array = null;
        }
        populateValues(length);
    }
    
    /**
     * Method to populate the Arrays / LinkedLists
     * 
     * @param length Number of x values, larger will generate a more accurate graph
     */
    private void populateValues(int length) {
        
        if(useArray) {
            int index = 0;
            
            for (int i = 0; i < length; i++) {
                double x_value = i + offset;
                x_array[index] = x_value;
                y_array[index] = computeY(x_value);
                index++;
            }
        } else {
            for (int i = 0; i < length; i++) {
                double x_value = i + offset;
                x_list.add(x_value);
                y_list.add(computeY(x_value));
            }
        }
    }
    
    /**
     * Abstract method
     * This is the f(x) function.
     * 
     * @param x The x value
     * @return y value
     */
    abstract double computeY(double x);
    
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
