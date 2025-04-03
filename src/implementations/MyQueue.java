package implementations;

import utilities.Iterator;
import utilities.QueueADT;

import java.util.Arrays;
import java.util.NoSuchElementException;

import exceptions.EmptyQueueException;

public class MyQueue<E> implements QueueADT<E>
{
	private MyDLL<E> queue;

	public MyQueue() {
		this.queue = new MyDLL<E>();
	}
	
	public MyQueue(int size) {
		this.queue = new MyDLL<E>();
	}
	
	@Override
	public void enqueue(E toAdd) throws NullPointerException
	{
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null element to the Queue.");
		}
		queue.add(toAdd);
	}

	@Override
	public E dequeue() throws EmptyQueueException
	{
		if (queue.isEmpty()) {
			throw new EmptyQueueException("Cannot dequeue when Queue is empty.");
		}
		return queue.remove(queue.get(0));
	}

	@Override
	public E peek() throws EmptyQueueException
	{
		if (queue.isEmpty()) {
			throw new EmptyQueueException("Cannot peek when Queue is empty.");
		}
		return queue.get(0);
	}

	@Override
	public void dequeueAll()
	{
		queue.clear();
	}

	@Override
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		if (toFind == null) {
			throw new NullPointerException("Cannot find a null element.");
		}
		return queue.contains(toFind);
	}

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

	@Override
	public Object[] toArray()
	{
		return queue.toArray();
	}

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

	@Override
	public boolean isFull()
	{
		return false;
	}

	@Override
	public int size()
	{
		return queue.size();
	}
	
	// ====================================================== //
	// 						Iterator
	
	// Constructor
	@Override
	public Iterator<E> iterator()
	{
		return new QueueIterator();
	}
	
	// Class Declaration
	private class QueueIterator implements Iterator<E> 
	{	
		private int index = 0;
		
		public QueueIterator() { }
		
		public int getIndex()
		{
			return index;
		}
		
		public boolean hasNext()
		{
			return index < queue.size();
		}
		
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