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
public class LinkedList {
    private final DoubleNode head;
    public int length = 0;
    
    public LinkedList() {
        head = new DoubleNode();
    }
    
    public void add(double e) {
        if(head.getNext() == null) {
            head.setNext(new DoubleNode(e, null));
        } else {
            DoubleNode tail = head;

            while(tail.getNext() != null) {
                tail = tail.getNext();
            }

            tail.setNext(new DoubleNode(e, null));
        }
        length++;
    }
    
    @Override
    public String toString() {
        String out = "";
        
        DoubleNode tail = head;
        
        while(tail.getNext() != null) {
            tail = tail.getNext();
            out += tail.getElement() + ", ";
        }
        
        
        return "{ " + out.substring(0, out.length()-2) + " }";
    }
    
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
