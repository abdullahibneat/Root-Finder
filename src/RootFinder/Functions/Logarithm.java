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
public class Logarithm extends Function {
    
    public Logarithm(boolean useArray) {
        this(useArray, 40);
    }

    public Logarithm(boolean useArray, int length) {
        super(useArray, length, -0.99999);
    }

    @Override
    double computeY(double x) {
        return Math.log(x + 1) + 1;
    }
}