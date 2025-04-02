package implementations;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.StackADT;

public class MyStack<E> implements StackADT<E> {
    private static final long serialVersionUID = 1L; // 1 Long
    
    private MyArrayList<E> stack;

    public MyStack() {
        this.stack = new MyArrayList<>();
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element to stack.");
        }
        stack.add(toAdd);
    }

    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.remove(stack.size() - 1);
    }

    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.get(stack.size() - 1);
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[stack.size()];
        for (int i = 0; i < stack.size(); i++) {
            array[i] = stack.get(stack.size() - 1 - i);
        }
        return array;
    }

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

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null element.");
        }
        return stack.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i).equals(toFind)) {
                return stack.size() - i;
            }
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }

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

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean stackOverflow() {
        return false;
    }

    private class StackIterator implements Iterator<E> {
        private int currentIndex = stack.size() - 1;

        @Override
        public boolean hasNext() {
            return currentIndex >= 0;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return stack.get(currentIndex--);
        }
    }
}
