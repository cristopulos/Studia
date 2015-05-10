package aisd4.queue;

import java.util.Iterator;

import aisd.lab3.lista.LinkedList;

public class Kolejka<T> implements Queue<T>, Iterable<T>{

	LinkedList<T> list = new LinkedList<T>();
	int capacity;
	
	public Kolejka (int capacity)
	{
		this.capacity = capacity;
	}
	
	public Kolejka ()
	{
		
	}
	
	@Override
	public void insert(T arg0) {
		list.addLast(arg0);
	}

	@Override
	public T remove() {
		return list.removeFirst();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean isFull() {
		return list.size()>=capacity;
	}
	
	public String toString ()
	{
		return list.printList();
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

}
