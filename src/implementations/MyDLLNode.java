package implementations;

/**
 * Node class for Double Linked List implementation.
 * 
 * @param <E> The type of element this node holds.
 */
public class MyDLLNode<E> {
    private E element;
    private MyDLLNode<E> next;
    private MyDLLNode<E> prev;
    
    /**
     * Creates a new node with the specified element.
     * 
     * @author Ahmad
     * @param element The element to be stored in this node.
     */
    public MyDLLNode(E element) {
        this.element = element;
        this.next = null;
        this.prev = null;
    }
    
    /**
     * Creates a new node with the specified element, next and previous nodes.
     * 
     * @param element The element to be stored in this node.
     * @param next The next node in the list.
     * @param prev The previous node in the list.
     */
    public MyDLLNode(E element, MyDLLNode<E> next, MyDLLNode<E> prev) {
        this.element = element;
        this.next = next;
        this.prev = prev;
    }
    
    /**
     * Gets the element stored in this node.
     * 
     * @return The element stored in this node.
     */
    public E getElement() {
        return element;
    }
    
    /**
     * Sets the element stored in this node.
     * 
     * @param element The element to be stored in this node.
     */
    public void setElement(E element) {
        this.element = element;
    }
    
    /**
     * Gets the next node in the list.
     * 
     * @return The next node in the list.
     */
    public MyDLLNode<E> getNext() {
        return next;
    }
    
    /**
     * Sets the next node in the list.
     * 
     * @param next The next node in the list.
     */
    public void setNext(MyDLLNode<E> next) {
        this.next = next;
    }
    
    /**
     * Gets the previous node in the list.
     * 
     * @return The previous node in the list.
     */
    public MyDLLNode<E> getPrev() {
        return prev;
    }
    
    /**
     * Sets the previous node in the list.
     * 
     * @param prev The previous node in the list.
     */
    public void setPrev(MyDLLNode<E> prev) {
        this.prev = prev;
    }
}
