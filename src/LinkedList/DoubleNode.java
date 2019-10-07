/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedList;

/**
 *
 * @author Abdullah
 */
public class DoubleNode {
    private double element;
    private DoubleNode next;
    
    public DoubleNode() {
        this(0., null);
    }
    
    public DoubleNode(double e, DoubleNode n) {
        element = e;
        next = n;
    }
    
    public double getElement() { return element; }
    
    public DoubleNode getNext() { return next; }
    
    public void setElement(double e) { element = e; }
    
    public void setNext(DoubleNode n) { next = n; }
}
