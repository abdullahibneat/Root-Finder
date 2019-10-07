/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RootFinder.Functions;

import LinkedList.LinkedList;

/**
 *
 * @author Abdullah
 */
abstract class Function {
    
    private final double[] x_array;
    private final double[] y_array;
    
    private final LinkedList x_list;
    private final LinkedList y_list;
    
    private final boolean useArray;
    
    private final double offset;
    
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
    
    abstract double computeY(double x);
    
    public double[] getX() {
        if(useArray) {
            return x_array;
        } else {
            return x_list.toDoubleArray();
        }
    }
    
    public double[] getY() {
        if(useArray) {
            return y_array;
        } else {
            return y_list.toDoubleArray();
        }
    }
}
