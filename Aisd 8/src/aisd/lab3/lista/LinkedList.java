package aisd.lab3.lista;

import java.util.Iterator;


public class LinkedList<T> implements Iterable<T> {

	private Element<T> first;
	private int size;

	public LinkedList() {
		first = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public Element<T> getFirst() {
		return first;
	}

	public void addLast(T arg0) {
		size++;
		if (first == null) {
			first = new Element<T>(arg0, first);
		} else {
			Element<T> el = first;
			while (el.getNext() != null) {
				el = el.getNext();
			}
			el.setNext(new Element<T>(arg0, el.getNext()));
		}
	}

	void addFirst(T arg0) {
		Element<T> temp = new Element<T>(arg0, first);
		first = temp;
		size++;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int getPos(T arg0) {
		Iterator<T> it = iterator();
		T curr = null;
		int counter = -1;
		while (it.hasNext()) {
			curr = it.next();
			counter++;
			if (curr.equals(arg0)) {
				return counter;
			}
		}
		return -1;
	}

	public T get(int index) {
		if (index >= size)
			throw new IndexOutOfBoundsException();
		Iterator<T> it = iterator();
		T curr = null;
		int counter = -1;
		while (it.hasNext()) {
			curr = it.next();
			counter++;
			if (counter == index)
				break;
		}
		return curr;
	}

	public T remove(T arg0) {
		Iterator<T> it = iterator();
		T curr = null;
		while (it.hasNext()) {
			curr = it.next();
			if (curr.equals(arg0)) {
				it.remove();
				return curr;
			}
		}
		return null;
	}

	public String printList() {
		StringBuffer result = new StringBuffer();
		for (T t : this) {
			result.append(String.format("%s ", t.toString()));
		}
		return result.toString();
	}

	public T removeFirst() {
		Iterator<T> it = iterator();
		T curr = it.next();
		it.remove();
		return curr;
	}

	@Override
	public Iterator<T> iterator() {
		return new ListIterator<T>(this);
	}

	void decrementSize() {
		size--;
	}

	void setFirst(Element<T> first) {
		this.first = first;
	}
}
