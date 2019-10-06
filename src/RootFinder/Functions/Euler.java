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
public class Euler extends Function {
    
    public Euler() {
        this(10);
    }

    public Euler(int length) {
        super(length, -length/2);
    }

    @Override
    double computeY(double x) {
        return Math.exp(x) - 3*x;
    }
}