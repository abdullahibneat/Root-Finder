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
            double Xnplus1 = Xn - (f.computeY(Xn) / f.computeYderivative(Xn));
            out.add(Xnplus1);
            if(Math.abs(Xn - Xnplus1) <= precision) break;
        }
        return out;
    }
    
    /**
     * Implementation of the Secant method to find the root.
     * 
     * @param f Function whose roots need to be found
     * @param x0 First starting point
     * @param x1  Second starting point
     * @param precision Degree of accuracy for this method to stop
     * 
     * @return LinkedList containing the iterations of the x0 value.
     */
    public double[] secant(Function f, double x0, double x1, double precision) {
        // Function must use Array
        f.useArray(true);
        
        double[] out = {x0, x1};
        while(true) {
            double Xn_1 = out[out.length-1];
            double Xn_2 = out[out.length-2];
            // Secant method formula
            double Xn = Xn_1 - f.computeY(Xn_1) * (Xn_1 - Xn_2) / (f.computeY(Xn_1) - f.computeY(Xn_2));
            double[] out_copy = out.clone();
            out = new double[out.length + 1];
            System.arraycopy(out_copy, 0, out, 0, out_copy.length);
            out[out.length - 1] = Xn;
            if(Math.abs(Xn - Xn_1) < precision) break;
        }
        return out;
    }
    
    /**
     * Implementation of the Bisection method to find the root.
     * 
     * @param f Function whose roots need to be found
     * @param x0 First starting point
     * @param x1  Second starting point
     * @param precision Degree of accuracy for this method to stop
     * 
     * @return LinkedList containing the iterations of the x0 value.
     * @throws java.lang.Exception Bisection requires f(x0) and f(x1) to be of opposite sign. Throws error if this condition is not fulfilled.
     */
    public double[] bisection(Function f, double x0, double x1, double precision) throws Exception{
        // Function must use Array
        f.useArray(true);
        
        double[] out = {x0, x1};
        
        double a = out[out.length-1];
        double b = out[out.length-2];
        
        // If f(a) and f(b) are of opposite sign, carry on with the bisection method, else throw exception
        if((f.computeY(a) > 0 && f.computeY(b) < 0) || (f.computeY(a) < 0 && f.computeY(b) > 0)) {        
            while(true) {
                double c = (a + b) / 2;

                double Fa = f.computeY(a);
                double Fc = f.computeY(c);

                double[] out_copy = out.clone();
                out = new double[out.length + 1];
                System.arraycopy(out_copy, 0, out, 0, out_copy.length);
                out[out.length - 1] = c;

                if((Fc < 0 && Fa > 0) || (Fc > 0 && Fa < 0)) {
                    b = c;
                    if(Math.abs(c - a) < precision) break;
                } else {
                    a = c;
                    if(Math.abs(c - b) < precision) break;
                }
            }
            return out;
        } else {
            throw new Exception("f(x0) and f(x1) must be of opposite sign");
        }
    }
}
