package NumericalMethods;

import LinkedList.LinkedList;
import RootFinder.Functions.Function;

/**
 * Class containing numerical methods to find roots given a function.
 *
 * @author Abdullah
 */
public class NumericalMethods {
    
    /**
     * Implementation of the Newton-Raphson method to find the root.
     * 
     * @param f Function whose roots need to be found
     * @param x0 Starting point
     * @param precision Degree of accuracy for this method to stop
     * 
     * @return LinkedList containing the iterations of the x0 value.
     */
    public LinkedList newtonRaphson(Function f, double x0, double precision) {
        // Function must use LinkedList
        f.useArray(false);
        
        LinkedList out = new LinkedList();
        out.add(x0);
        while(true) {
            double Xn = out.getLastElement();
            // Newton-Raphson formula
            double Xnplus1 = computeNewtonRaphson(f, Xn);
            out.add(Xnplus1);
            if(Math.abs(Xn - Xnplus1) <= precision) break;
        }
        return out;
    }
    
    /**
     * Newton-Raphson formula
     */
    private double computeNewtonRaphson(Function f, double x) {
        return x - (f.computeY(x) / f.computeYderivative(x));
    }
    
    /**
     * Implementation of the Secant method to find the root.
     * 
     * @param f Function whose roots need to be found
     * @param x0 First starting point
     * @param x1  Second starting point
     * @param precision Degree of accuracy for this method to stop
     * 
     * @return Array of double containing the iterations of the x0 value.
     */
    public double[] secant(Function f, double x0, double x1, double precision) {
        // Function must use Array
        f.useArray(true);
        
        double[] out = {x0, x1};
        while(true) {
            double Xn_1 = out[out.length-1];
            double Xn_2 = out[out.length-2];
            // Secant method formula
            double Xn = computeSecant(f, Xn_1, Xn_2);
            
            // Create a new array 1 size bigger than the previous one
            // so I can add the new x value to the end
            double[] out_copy = out.clone();
            out = new double[out.length + 1];
            System.arraycopy(out_copy, 0, out, 0, out_copy.length);
            out[out.length - 1] = Xn;
            
            if(Math.abs(Xn - Xn_1) <= precision) break;
        }
        return out;
    }
    
    /**
     * Secant method formula
     */
    private double computeSecant(Function f, double Xn_1, double Xn_2) {
        return Xn_1 - f.computeY(Xn_1) * (Xn_1 - Xn_2) / (f.computeY(Xn_1) - f.computeY(Xn_2));
    }
    
    /**
     * Implementation of the Bisection method to find the root.
     * 
     * @param f Function whose roots need to be found
     * @param x0 First starting point
     * @param x1  Second starting point
     * @param precision Degree of accuracy for this method to stop
     * 
     * @return Array of double containing the iterations of the x0 value.
     * @throws java.lang.Exception Bisection requires f(x0) and f(x1) to be of opposite sign. Throws error if this condition is not fulfilled.
     */
    public double[] bisection(Function f, double x0, double x1, double precision) throws Exception{
        // Function must use Array
        f.useArray(true);
        
        double[] out = {x0, x1};
        
        double a = out[out.length-1];
        double b = out[out.length-2];
        
        // If f(a) and f(b) are of opposite sign, carry on with the bisection method, else throw exception
        if(f.computeY(a) * f.computeY(b) < 0) {        
            while(true) {
                double c = computeBisection(a, b);

                double Fa = f.computeY(a);
                double Fc = f.computeY(c);
                
                double last = out[out.length - 1]; // Keep track of the previous c
                
                // Create a new array 1 size bigger than the previous one
                // so I can add the new x value to the end
                double[] out_copy = out.clone();
                out = new double[out.length + 1];
                System.arraycopy(out_copy, 0, out, 0, out_copy.length);
                out[out.length - 1] = c;

                if(Math.abs(c - last) <= precision) break; // Stop if difference between this c and previous c is <= precision
                
                if(Fc * Fa < 0) b = c; else  a = c;
            }
            return out;
        } else {
            throw new Exception("f(x0) and f(x1) must be of opposite sign");
        }
    }
    
    /**
     * Method to find the middle point for bisection
     */
    private double computeBisection(double a, double b) {
        return (a + b) / 2;
    }
    
    /**
     * Implementation of the False Position method to find the root.
     * 
     * False position is using the bisection method, but instead of taking the middle point between x0 and x1,
     * it takes the secant between the two points.
     * 
     * @param f Function whose roots need to be found
     * @param x0 First starting point
     * @param x1  Second starting point
     * @param precision Degree of accuracy for this method to stop
     * 
     * @return LinkedList containing the iterations of the x0 value.
     * @throws java.lang.Exception Bisection requires f(x0) and f(x1) to be of opposite sign. Throws error if this condition is not fulfilled.
     */
    public LinkedList falsePosition(Function f, double x0, double x1, double precision) throws Exception{
        // Function must use Array
        f.useArray(false);
        
        LinkedList out = new LinkedList();
        out.add(x0);
        out.add(x1);
        
        double a = x0;
        double b = x1;
        
        // If f(a) and f(b) are of opposite sign, carry on with the bisection method, else throw exception
        if(f.computeY(a) * f.computeY(b) < 0) {        
            while(true) {
                double c = computeSecant(f, a, b);

                double Fa = f.computeY(a);
                double Fc = f.computeY(c);

                double last = out.getLastElement(); // Keep track of the previous c
                out.add(c);

                if(Math.abs(c - last) <= precision) break; // Stop if difference between this c and previous c is <= precision
                
                if(Fc * Fa < 0) b = c; else  a = c;
            }
            return out;
        } else {
            throw new Exception("f(x0) and f(x1) must be of opposite sign");
        }
    }
}
