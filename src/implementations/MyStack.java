package implementations;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.StackADT;

/**
 * Implementation of the StackADT interface using an ArrayList.
 * This class provides a LIFO (Last-In-First-Out) stack implementation
 * using the MyArrayList class as the underlying data structure.
 * 
 * @param <E> The type of elements this stack holds.
 * @version 1.0
 * @see utilities.StackADT
 * @see implementations.MyArrayList
 */
public class MyStack<E> implements StackADT<E> {
    // The serialVersionUID for serializable class
    private static final long serialVersionUID = 1L; // 1 Long

    private MyArrayList<E> stack;

    /**
     * Default constructor.
     * Creates an empty stack with the default capacity.
     */
    public MyStack() {
        this.stack = new MyArrayList<>();
    }

    /**
     * Pushes an element onto the top of this stack.
     * 
     * @param toAdd The element to be pushed onto this stack.
     * @throws NullPointerException if the specified element is null.
     */
    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element to stack.");
        }
        stack.add(toAdd);
    }

    /**
     * Removes and returns the element at the top of this stack.
     * 
     * @return The element at the top of this stack.
     * @throws EmptyStackException if this stack is empty.
     */
    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.remove(stack.size() - 1);
    }

    /**
     * Returns, but does not remove, the element at the top of this stack.
     * 
     * @return The element at the top of this stack.
     * @throws EmptyStackException if this stack is empty.
     */
    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.get(stack.size() - 1);
    }

    // Removes all elements from this stack. The stack will be empty
    @Override
    public void clear() {
        stack.clear();
    }

    // Returns true if this stack contains no elements
    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * Returns an array containing all of the elements in this stack in proper sequence
     * (from the top of the stack to the bottom).
     * 
     * @return An array containing all of the elements in this stack in proper sequence.
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[stack.size()];
        for (int i = 0; i < stack.size(); i++) {
            array[i] = stack.get(stack.size() - 1 - i);
        }
        return array;
    }
    
    /**
     * Returns an array containing all of the elements in this stack in proper sequence
     * (from the top of the stack to the bottom); the runtime type of the returned array
     * is that of the specified array.
     * 
     * @param holder The array into which the elements of this stack are to be stored,
     *               if it is big enough; otherwise, a new array of the same runtime
     *               type is allocated for this purpose.
     * @return An array containing the elements of this stack.
     * @throws NullPointerException if the specified array is null.
     */
    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) {
            throw new NullPointerException("Input array cannot be null.");
        }
        if (holder.length < stack.size()) {
            holder = (E[]) java.lang.reflect.Array.newInstance(
                holder.getClass().getComponentType(), stack.size());
        }
        for (int i = 0; i < stack.size(); i++) {
            holder[i] = stack.get(stack.size() - 1 - i);
        }
        if (holder.length > stack.size()) {
            holder[stack.size()] = null;
        }
        return holder;
    }
    
    /**
     * Returns true if this stack contains the specified element.
     * 
     * @param toFind The element whose presence in this stack is to be tested.
     * @return true if this stack contains the specified element.
     * @throws NullPointerException if the specified element is null.
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null element.");
        }
        return stack.contains(toFind);
    }
    
    /**
     * Returns the 1-based position of the specified element in this stack,
     * or -1 if this stack does not contain the element.
     * The topmost element on the stack is considered to be at distance 1.
     * 
     * @param toFind The element to search for.
     * @return The 1-based position of the element from the top of the stack,
     *         or -1 if the element is not found.
     */
    @Override
    public int search(E toFind) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i).equals(toFind)) {
                return stack.size() - i;
            }
        }
        return -1;
    }
    
    /**
     * Returns an iterator over the elements in this stack in proper sequence
     * (from the top of the stack to the bottom).
     * 
     * @return An iterator over the elements in this stack in proper sequence.
     */
    @Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }
    
    /**
     * Compares the specified stack with this stack for equality.
     * Returns true if the specified object is a stack of the same type,
     * has the same size, and contains the same elements in the same order.
     * 
     * @param that The stack to be compared for equality with this stack.
     * @return true if the specified stack is equal to this stack.
     */
    @Override
    public boolean equals(StackADT<E> that) {
        if (this == that) {
            return true;
        }
        if (that == null || this.size() != that.size()) {
            return false;
        }

        Iterator<E> thisIterator = this.iterator();
        Iterator<E> thatIterator = that.iterator();

        while (thisIterator.hasNext() && thatIterator.hasNext()) {
            if (!thisIterator.next().equals(thatIterator.next())) {
                return false;
            }
        }

        return true;
    }

    // Return the number of elements in this stack
    @Override
    public int size() {
        return stack.size();
    }

    // Returns true if this stack is currently full
    @Override
    public boolean stackOverflow() {
        return false;
    }
    
    /**
     * Private iterator implementation for MyStack.
     * Provides a way to iterate through the elements of the stack from top to bottom.
     */
    private class StackIterator implements Iterator<E> {
        private int currentIndex = stack.size() - 1;

        // Returns true if the iteration has more elements
        @Override
        public boolean hasNext() {
            return currentIndex >= 0;
        }

        // Returns the next element in the iteration
        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return stack.get(currentIndex--);
        }
    }
}
