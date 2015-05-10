package aisd.lab3.lista;

import java.util.Iterator;

import aisd.lab3.lista.Element;
import aisd.lab3.lista.LinkedList;

public class ListIterator<T> implements Iterator<T> {

	private Element<T> current;
	private Element<T> previous = null;
	private Element<T> morePrevious = null;
	private LinkedList<T> list;
	private boolean wasRemoved = false;

	public ListIterator(LinkedList<T> list) {
		this.list = list;
		current = list.getFirst();
	}

	@Override
	public boolean hasNext() {
		return (current != null);
	}

	@Override
	public T next() {
		T output = current.getItem();
		if (wasRemoved)
			wasRemoved = false;
		else
			morePrevious = previous;
		previous = current;
		current = current.getNext();
		return output;
	}

	@Override
	public void remove() {
		if (previous == null)
			throw new NullPointerException();
		else {
			list.decrementSize();
			if (morePrevious == null) {
				list.setFirst(current);
			} else {
				morePrevious.setNext(current);
				wasRemoved = true;
			}
		}
	}
}
