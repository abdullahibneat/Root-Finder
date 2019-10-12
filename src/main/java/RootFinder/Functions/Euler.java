package RootFinder.Functions;

/**
 * Implementation of the function f(x) = e^x - 3x
 *
 * @author Abdullah
 */
public class Euler extends Function {
    
    /**
     * Constructor with default length
     *
     * @param useArray Set to true to use Array, false for LinkedList
     */
    public Euler(boolean useArray) {
        this(useArray, 10);
    }

    /**
     * Constructor with custom length
     *
     * @param useArray Set to true to use Array, false for LinkedList
     * @param length Number of x values, larger will generate a more accurate graph
     */
    public Euler(boolean useArray, int length) {
        super(useArray, length, -length/2, 4);
    }

    /**
     * Constructor with custom length and custom offset
     *
     * @param useArray Set to true to use Array, false for LinkedList
     * @param length Number of x values, larger will generate a more accurate graph
     * @param offset Move the graph horizontally according to this offset
     * @param precision Increase this value to smoothen the graph
     */
    public Euler(boolean useArray, int length, double offset, int precision) {
        super(useArray, length, offset, precision);
    }

    /**
     * This is the f(x) function.
     * 
     * @param x The x value
     * @return y value
     */
    @Override
    double computeY(double x) {
        return Math.exp(x) - 3*x;
    }
}