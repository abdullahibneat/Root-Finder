package LinkedList;

/**
 * Implementation of the linked list data structure
 *
 * @author Abdullah
 */
public class LinkedList {
    
    private final DoubleNode head;
    private DoubleNode tail; // Keep track of the tail

    /**
     * Current length of this list
     */
    public int length = 0;
    
    /**
     * Constructor
     * 
     * Initialise an empty linked list
     */
    public LinkedList() {
        head = new DoubleNode();
    }
    
    /**
     * Add an element to the list
     *
     * @param e Double number
     */
    public void add(double e) {
        
        // Create the node
        DoubleNode n = new DoubleNode(e, null);
        
        // If this is the first element
        if(head.getNext() == null) {
            tail = n;
            head.setNext(tail);
        } else {
            tail.setNext(n);
            tail = n;
        }
        
        // Increase length of the list by 1
        length++;
    }
    
    /**
     * Return the last element in the LinkedList
     * 
     * @return Last element
     */
    public double getLastElement() {
        return tail.getElement();
    }
    
    /**
     * Convert linked list to String
     * 
     * @return Stringified linked list
     */
    @Override
    public String toString() {
        String out = "";
        
        DoubleNode node = head;
        while(node.getNext() != null) {
            node = node.getNext();
            out += node.getElement() + ", ";
        }
        
        // Substring removes the ", " from the end of the string
        return "{ " + out.substring(0, out.length()-2) + " }";
    }
    
    /**
     * Convert the list to an array of doubles
     *
     * @return List as double[]
     */
    public double[] toDoubleArray() {
        double[] out = new double[length];
        
        DoubleNode n = head;
        
        for (int i = 0; i < length; i++) {
            n = n.getNext();
            out[i] = n.getElement();
        }
        
        return out;
    }
}
