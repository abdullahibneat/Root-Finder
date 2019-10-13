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
        super(useArray, length, -4.38, 4);
    }

    /**
     * Constructor with custom length and custom offset
     *
     * @param useArray Set to true to use Array, false for LinkedList
     * @param length Number of x values, larger will generate a more accurate graph
     * @param offset Move the graph horizontally according to this offset
     * @param precision Increase this value to smoothen the graph
     */
    public Quadratic(boolean useArray, int length, double offset, int precision) {
        super(useArray, length, offset, precision);
    }

    /**
     * This is the f(x) function.
     * 
     * @param x The x value
     * @return y value
     */
    @Override
    public double computeY(double x) {
        return x - Math.pow(x, 2);
    }
    
    /**
     * Abstract method
     * This is the f'(x) function.
     * 
     * @param x The x value
     * @return derivative at x
     */
    @Override
    public double computeYderivative(double x) {
        return 1 - 2*x;
    };
}