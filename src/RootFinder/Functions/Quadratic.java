package RootFinder.Functions;

/**
 * Implementation of the function f(x) = x - x^2
 *
 * @author Abdullah
 */
public class Quadratic extends Function {
    
    /**
     * Constructor with default length
     *
     * @param useArray Set to true to use Array, false for LinkedList
     */
    public Quadratic(boolean useArray) {
        this(useArray, 10);
    }

    /**
     * Constructor with custom length
     *
     * @param useArray Set to true to use Array, false for LinkedList
     * @param length Number of x values, larger will generate a more accurate graph
     */
    public Quadratic(boolean useArray, int length) {
        super(useArray, length, -length/2 + 1);
    }

    /**
     * This is the f(x) function.
     * 
     * @param x The x value
     * @return y value
     */
    @Override
    double computeY(double x) {
        return x - Math.pow(x, 2);
    }
}