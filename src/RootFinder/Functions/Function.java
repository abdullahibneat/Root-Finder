/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RootFinder.Functions;

/**
 *
 * @author Abdullah
 */
abstract class Function {
    
    private final double[] x;
    private final double[] y;
    
    private final double offset;
    
    public Function(int length, double off) {
        offset = off;
        x = new double[length];
        y = new double[length];
        populateArrays(length);
    }
    
    private void populateArrays(int length) {
        int index = 0;
        for (int i = 0; i < length; i++) {
            double x_value = i + offset;
            x[index] = x_value;
            y[index] = computeY(x_value);
            index++;
        }
    }
    
    abstract double computeY(double x);
    
    public double[] getX() { return x; }
    
    public double[] getY() { return y; }
}
