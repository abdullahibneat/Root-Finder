package LinkedList;

/**
 * Implementation of the linked list data structure
 *
 * @author Abdullah
 */
public class LinkedList {
    private final DoubleNode head;

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
        
        // If this is the first element
        if(head.getNext() == null) {
            head.setNext(new DoubleNode(e, null));
        } else {
            DoubleNode tail = head;

            // Find the last element of the list
            while(tail.getNext() != null) {
                tail = tail.getNext();
            }

            // Add the new element to the end of the list
            tail.setNext(new DoubleNode(e, null));
        }
        
        // Increase length of the list by 1
        length++;
    }
    
    /**
     * Convert linked list to String
     * 
     * @return Stringified linked list
     */
    @Override
    public String toString() {
        String out = "";
        
        DoubleNode tail = head;
        while(tail.getNext() != null) {
            tail = tail.getNext();
            out += tail.getElement() + ", ";
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
        
        DoubleNode tail = head;
        
        for (int i = 0; i < length; i++) {
            tail = tail.getNext();
            out[i] = tail.getElement();
        }
        
        return out;
    }
}
