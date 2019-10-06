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
public class Quadratic extends Function {
    
    public Quadratic() {
        this(10);
    }

    public Quadratic(int length) {
        super(length, -length/2 + 1);
    }

    @Override
    double computeY(double x) {
        return x - Math.pow(x, 2);
    }
}