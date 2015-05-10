package aisd.lab3.lista;

import java.util.Iterator;

public class SortedListIterator<T extends Comparable<T>> implements Iterator<T> {

	private SortedElement<T> current;
	private SortedElement<T> previous = null;
	private SortedElement<T> morePrevious = null;
	private SortedLinkedList<T> list;
	private boolean wasRemoved = false;

	public SortedListIterator(SortedLinkedList<T> list) {
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
