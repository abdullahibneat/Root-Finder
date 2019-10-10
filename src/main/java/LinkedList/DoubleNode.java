package LinkedList;

/**
 * Implementation of a node for the linked list data structure.
 * The element of this type of node is a double.
 *
 * @author Abdullah
 */
public class DoubleNode {
    private double element;
    private DoubleNode next;
    
    /**
     * Constructor
     * 
     * Initialises an empty node.
     */
    public DoubleNode() {
        this(0., null);
    }
    
    /**
     * Constructor
     * 
     * Initialises a node with elements specified as parameters.
     *
     * @param e Double number to be stored in the list
     * @param n Reference to the next node on the list
     */
    public DoubleNode(double e, DoubleNode n) {
        element = e;
        next = n;
    }
    
    /**
     * @return Double number of this node
     */
    public double getElement() { return element; }
    
    /**
     * @return Reference to the next node
     */
    public DoubleNode getNext() { return next; }
    
    /**
     * Set the double number of this node
     *
     * @param e Double number
     */
    public void setElement(double e) { element = e; }
    
    /**
     * Set the reference to the next node
     *
     * @param n Node
     */
    public void setNext(DoubleNode n) { next = n; }
}
