package implementations;

import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.ListADT;

/**
 * Doubly Linked List implementation.
 * 
 * @author Ahmad
 * @param <E> The type of elements this list holds.
 */
public class MyDLL<E> implements ListADT<E> {
    
    private static final long serialVersionUID = 1L;
    
    public MyDLLNode<E> head;
    private MyDLLNode<E> tail;
    private int size;
    
    /**
     * Default constructor creates an empty list.
     */
    public MyDLL() {
        head = tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        // Special case: adding at the end of the list
        if (index == size) {
            return add(toAdd);
        }
        
        // Special case: adding at the beginning of the list
        if (index == 0) {
            MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
            newNode.setNext(head);
            
            if (head != null) {
                head.setPrev(newNode);
            } else {
                // List was empty
                tail = newNode;
            }
            
            head = newNode;
            size++;
            return true;
        }
        
        // Find the node at the specified index
        MyDLLNode<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        
        // Insert the new node before the current node
        MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
        newNode.setPrev(current.getPrev());
        newNode.setNext(current);
        
        current.getPrev().setNext(newNode);
        current.setPrev(newNode);
        
        size++;
        return true;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }
        
        MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
        
        if (tail == null) {
            // List is empty
            head = newNode;
            tail = newNode;
        } else {
            // Add to the end of the list
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        
        size++;
        return true;
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null list");
        }
        
        boolean modified = false;
        Iterator<? extends E> iterator = toAdd.iterator();
        while (iterator.hasNext()) {
            add(iterator.next());
            modified = true;
        }
        
        return modified;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        MyDLLNode<E> current;
        
        // Optimize traversal by starting from either head or tail
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        
        return current.getElement();
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        // Special case: removing the only element
        if (size == 1) {
            E element = head.getElement();
            head = null;
            tail = null;
            size = 0;
            return element;
        }
        
        // Special case: removing the first element
        if (index == 0) {
            E element = head.getElement();
            head = head.getNext();
            head.setPrev(null);
            size--;
            return element;
        }
        
        // Special case: removing the last element
        if (index == size - 1) {
            E element = tail.getElement();
            tail = tail.getPrev();
            tail.setNext(null);
            size--;
            return element;
        }
        
        // Find the node at the specified index
        MyDLLNode<E> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        
        // Remove the node
        E element = current.getElement();
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        
        size--;
        return element;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Cannot remove null element");
        }
        
        MyDLLNode<E> current = head;
        int index = 0;
        
        while (current != null) {
            if (toRemove.equals(current.getElement())) {
                return remove(index);
            }
            current = current.getNext();
            index++;
        }
        
        return null;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) {
            throw new NullPointerException("Cannot set null element");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        MyDLLNode<E> current;
        
        // Optimize traversal by starting from either head or tail
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        
        E previousElement = current.getElement();
        current.setElement(toChange);
        return previousElement;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null element");
        }
        
        MyDLLNode<E> current = head;
        
        while (current != null) {
            if (toFind.equals(current.getElement())) {
                return true;
            }
            current = current.getNext();
        }
        
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("Array cannot be null");
        }
        
        // If the provided array is too small, create a new one of the same type
        if (toHold.length < size) {
            toHold = (E[]) java.lang.reflect.Array.newInstance(
                    toHold.getClass().getComponentType(), size);
        }
        
        MyDLLNode<E> current = head;
        for (int i = 0; i < size; i++) {
            toHold[i] = current.getElement();
            current = current.getNext();
        }
        
        // If the array is larger than the list, set the next element to null
        if (toHold.length > size) {
            toHold[size] = null;
        }
        
        return toHold;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        MyDLLNode<E> current = head;
        
        for (int i = 0; i < size; i++) {
            array[i] = current.getElement();
            current = current.getNext();
        }
        
        return array;
    }

    @Override
    public Iterator<E> iterator() {
        return new DLLIterator();
    }
    
    /**
     * Private iterator implementation for MyDLL.
     */
    private class DLLIterator implements Iterator<E> {
        private MyDLLNode<E> current = head;
        
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list");
            }
            
            E element = current.getElement();
            current = current.getNext();
            return element;
        }
    }
}
