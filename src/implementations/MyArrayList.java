package implementations;

import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * ArrayList implementation using an array as the underlying data structure.
 * 
 * @param <E> The type of elements this list holds.
 */
public class MyArrayList<E> implements ListADT<E> {
    
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_CAPACITY = 10;
    
    private E[] array;
    private int size;
    
    /**
     * Default constructor initializes the array with default capacity.
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }
    
    /**
     * Constructor with specified capacity.
     * 
     * @param initialCapacity The initial capacity of the array.
     */
    @SuppressWarnings("unchecked")
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative: " + initialCapacity);
        }
        array = (E[]) new Object[initialCapacity];
        size = 0;
    }

    /**
     * Ensures the array has enough capacity to accommodate more elements.
     * 
     * @param minCapacity The minimum capacity needed.
     */
    @SuppressWarnings("unchecked")
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > array.length) {
            int newCapacity = Math.max(array.length * 2, minCapacity);
            E[] newArray = (E[]) new Object[newCapacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
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
        
        ensureCapacity(size + 1);
        
        // Shift elements to make room for the new element
        if (index < size) {
            System.arraycopy(array, index, array, index + 1, size - index);
        }
        
        array[index] = toAdd;
        size++;
        return true;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException {
        return add(size, toAdd);
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
        return array[index];
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        E removedElement = array[index];
        
        // Shift elements to fill the gap
        if (index < size - 1) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
        }
        
        array[--size] = null; // Clear the last element
        return removedElement;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Cannot remove null element");
        }
        
        for (int i = 0; i < size; i++) {
            if (toRemove.equals(array[i])) {
                return remove(i);
            }
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
        
        E previousElement = array[index];
        array[index] = toChange;
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
        
        for (int i = 0; i < size; i++) {
            if (toFind.equals(array[i])) {
                return true;
            }
        }
        
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("Array cannot be null");
        }
        
        if (toHold.length < size) {
            // If array is too small, create a new one of the same runtime type
            return (E[]) java.util.Arrays.copyOf(array, size, toHold.getClass());
        }
        
        System.arraycopy(array, 0, toHold, 0, size);
        
        if (toHold.length > size) {
            toHold[size] = null;
        }
        
        return toHold;
    }

    @Override
    public Object[] toArray() {
        return java.util.Arrays.copyOf(array, size);
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }
    
    /**
     * Private iterator implementation for MyArrayList.
     */
    private class ArrayListIterator implements Iterator<E> {
        private int currentIndex = 0;
        
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list");
            }
            return array[currentIndex++];
        }
    }
}