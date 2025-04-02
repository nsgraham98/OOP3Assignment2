package implementations;

import utilities.Iterator;
import utilities.QueueADT;

import java.util.NoSuchElementException;

import exceptions.EmptyQueueException;

public class MyQueue<E> implements QueueADT<E>
{

	private MyDLL<E> queue;
//	private QueueIterator iterator;

	public MyQueue() {
		this.queue = new MyDLL<E>();
	}
	
//	public void setIterator()
//	{
//		this.iterator = new QueueIterator();
//	}
	
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
		while (queue.get(0) != null) {
			queue.remove(0);
		}
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
		if (this == that) { return true; }
//			
//		while (thisIterator.hasNext() && thatIterator.hasNext())
//		{
//			if (thisIterator.next() != thatIterator.next())
//			{
//				return false;
//			}
//		}
		
	}

	@Override
	public Object[] toArray()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E[] toArray(E[] holder) throws NullPointerException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFull()
	{
		// TODO Auto-generated method stub
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
		// Index version
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
		
		// MyDLLNode version
//		private MyDLLNode<E> current;
//		
//		public QueueIterator() {
//			current = queue.head;
//		}
//		
//		public boolean hasNext()
//		{
//			if (current.getNext() == null)
//			{
//				return false;
//			}
//			return true;
//		}
//		
//		public E next() throws NoSuchElementException
//		{
//			if (!hasNext()) {
//				throw new NoSuchElementException();
//			}
//			
//			E element = current.getNext().getElement();
//			current = current.getNext();
//			return element;
//		}
	}
	
}