package implementations;

import utilities.Iterator;
import utilities.QueueADT;

import java.util.Arrays;
import java.util.NoSuchElementException;

import exceptions.EmptyQueueException;

/**
 * Implementation of the QueueADT interface using a Doubly Linked List.
 * This class provides a FIFO (First-In-First-Out) queue implementation
 * using the MyDLL class as the underlying data structure.
 * 
 * @author Nicholas
 *
 * @param <E> The type of elements this queue holds.
 * @version 1.0
 * @see utilities.QueueADT
 * @see implementations.MyDLL
 * @see exceptions.EmptyQueueException
 */

public class MyQueue<E> implements QueueADT<E>
{
	/** The underlying data structure to store queue elements */
	private MyDLL<E> queue;
	
	/**
	 * Default constructor.
	 * Creates an empty queue with the default capacity.
	 */
	public MyQueue() {
		this.queue = new MyDLL<E>();
	}
	
	/**
	 * Constructor with initial size.
	 * Creates an empty queue with the specified initial capacity.
	 * Note: Since this implementation uses a linked list, the size parameter
	 * is not actually used but is included for compatibility.
	 * 
	 * @param size The initial capacity of the queue (not used).
	 */
	public MyQueue(int size) {
		this.queue = new MyDLL<E>();
	}
	
	/**
	 * Adds the specified element to the end of this queue.
	 * 
	 * @param toAdd The element to be added to this queue.
	 * @throws NullPointerException if the specified element is null.
	 */
	@Override
	public void enqueue(E toAdd) throws NullPointerException
	{
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null element to the Queue.");
		}
		queue.add(toAdd);
	}
	
	/**
	 * Removes and returns the element at the front of this queue.
	 * 
	 * @return The element at the front of this queue.
	 * @throws EmptyQueueException if this queue is empty.
	 */
	@Override
	public E dequeue() throws EmptyQueueException
	{
		if (queue.isEmpty()) {
			throw new EmptyQueueException("Cannot dequeue when Queue is empty.");
		}
		return queue.remove(queue.get(0));
	}
	
	/**
	 * Returns, but does not remove, the element at the front of this queue.
	 * 
	 * @return The element at the front of this queue.
	 * @throws EmptyQueueException if this queue is empty.
	 */
	@Override
	public E peek() throws EmptyQueueException
	{
		if (queue.isEmpty()) {
			throw new EmptyQueueException("Cannot peek when Queue is empty.");
		}
		return queue.get(0);
	}
	
	/**
	 * Removes all of the elements from this queue.
	 * The queue will be empty after this call returns.
	 */
	@Override
	public void dequeueAll()
	{
		queue.clear();
	}
	
	/**
	 * Returns true if this queue contains no elements.
	 * 
	 * @return true if this queue contains no elements.
	 */
	@Override
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}
	
	/**
	 * Returns true if this queue contains the specified element.
	 * 
	 * @param toFind The element whose presence in this queue is to be tested.
	 * @return true if this queue contains the specified element.
	 * @throws NullPointerException if the specified element is null.
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		if (toFind == null) {
			throw new NullPointerException("Cannot find a null element.");
		}
		return queue.contains(toFind);
	}
	
	/**
	 * Returns the 1-based position of the specified element in this queue,
	 * or -1 if this queue does not contain the element.
	 * The first element in the queue is at position 1.
	 * 
	 * @param toFind The element to search for.
	 * @return The 1-based position of the element, or -1 if not found.
	 */
	@Override
	public int search(E toFind)
	{	
		QueueIterator iterator = new QueueIterator();
		
		while (iterator.hasNext())
		{
			if (iterator.next() == toFind)
			{
				return iterator.getIndex();
			}
		}
		return -1;
	}
	
	/**
	 * Compares the specified queue with this queue for equality.
	 * Returns true if the specified object is a queue of the same type,
	 * has the same size, and contains the same elements in the same order.
	 * 
	 * @param that The queue to be compared for equality with this queue.
	 * @return true if the specified queue is equal to this queue.
	 */
	@Override
	public boolean equals(QueueADT<E> that)
	{
		// Checks if in same memory location
		if (this == that) 
		{
			return true; 
		}
		
		if (that == null || this.size() != that.size())
		{
			return false;
		}
		
		Iterator<E> thisIterator = this.iterator();
	    Iterator<E> thatIterator = that.iterator();
		
		while (thisIterator.hasNext() && thatIterator.hasNext())
		{
			if (!thisIterator.next().equals(thatIterator.next()))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns an array containing all of the elements in this queue in proper sequence.
	 * 
	 * @return An array containing all of the elements in this queue in proper sequence.
	 */
	@Override
	public Object[] toArray()
	{
		return queue.toArray();
	}
	
	/**
	 * Returns an array containing all of the elements in this queue in proper sequence;
	 * the runtime type of the returned array is that of the specified array.
	 * 
	 * @param holder The array into which the elements of this queue are to be stored,
	 *              if it is big enough; otherwise, a new array of the same runtime
	 *              type is allocated for this purpose.
	 * @return An array containing the elements of this queue.
	 * @throws NullPointerException if the specified array is null.
	 */
	@Override
	public E[] toArray(E[] holder) throws NullPointerException
	{
		if (holder == null) 
		{
			throw new NullPointerException("Holding array cannot be null");
		}
		
		int qsize = queue.size();
		
		if (holder.length != qsize)
		{
			E[] newHolder = Arrays.copyOf(holder, qsize);
			return queue.toArray(newHolder);
		}
		
	    for (int i = 0; i < qsize; i++) 
	    {
	        holder[i] = queue.get(i);
	    }
	    
	    return holder;
	}
	
	/**
	 * Returns true if this queue is currently full.
	 * Note: This implementation always returns false as a linked list
	 * can grow as needed.
	 * 
	 * @return false (this queue implementation is never full).
	 */
	@Override
	public boolean isFull()
	{
		return false;
	}
	
	/**
	 * Returns the number of elements in this queue.
	 * 
	 * @return The number of elements in this queue.
	 */
	@Override
	public int size()
	{
		return queue.size();
	}
	
	// ====================================================== //
	// 						Iterator
	
	/**
 	 * Constructor
	 * Returns an iterator over the elements in this queue in proper sequence.
	 * 
	 * @return An iterator over the elements in this queue in proper sequence.
	 */
	@Override
	public Iterator<E> iterator()
	{
		return new QueueIterator();
	}
	
	
	/**
 	 * Class Declaration
	 * Private iterator implementation for MyQueue.
	 * Provides a way to iterate through the elements of the queue.
	 */
	private class QueueIterator implements Iterator<E> 
	{	
		//current position in the queue
		private int index = 0;

		//default constructor for QueueIterator
		public QueueIterator() { }

		//returns the current index in the iteration
		public int getIndex()
		{
			return index;
		}

		// returns true if the iteration has more elements
		public boolean hasNext()
		{
			return index < queue.size();
		}

		// returns the next element in the iteration
		public E next() throws NoSuchElementException
		{
			if (!hasNext())
			{
				throw new NoSuchElementException();
			}
			return queue.get(index++);
		}
	}
	
}
