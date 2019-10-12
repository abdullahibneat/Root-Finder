package RootFinder.Functions;

/**
 * Implementation of the function f(x) = ln(x+1) + 1
 *
 * @author Abdullah
 */
public class Logarithm extends Function {
    
    /**
     * Constructor with default length
     *
     * @param useArray Set to true to use Array, false for LinkedList
     */
    public Logarithm(boolean useArray) {
        this(useArray, 40);
    }

    /**
     * Constructor with custom length
     *
     * @param useArray Set to true to use Array, false for LinkedList
     * @param length Number of x values, larger will generate a more accurate graph
     */
    public Logarithm(boolean useArray, int length) {
        super(useArray, length, -0.99999, 4);
    }

    /**
     * Constructor with custom length and custom offset
     *
     * @param useArray Set to true to use Array, false for LinkedList
     * @param length Number of x values, larger will generate a more accurate graph
     * @param offset Move the graph horizontally according to this offset
     * @param precision Increase this value to smoothen the graph
     */
    public Logarithm(boolean useArray, int length, double offset, int precision) {
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
        return Math.log(x + 1) + 1;
    }
}