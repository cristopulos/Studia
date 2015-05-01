package aisd.util;

import java.io.PrintStream;
import java.util.Iterator;

public abstract class List<T> implements Iterable<T> {

	protected Element<T> first;
	protected int size;

	 public List() {
		first = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	 public Element<T> getFirst() {
		return first;
	}

	 public Element<T> add(T arg0, int pos) {

		Element<T> el = first;
		Element<T> prev = null;
		for (int i = 0; i < pos; i++) {
			prev = el;
			el = el.getNext();
		}
		Element<T> added = new Element<T>(arg0, el);
		if (prev != null)
			prev.setNext(added);
		return added;
	}

	 public Element<T> add(T arg0) {
		Element<T> temp = new Element<T>(arg0, first);
		first = temp;
		size++;
		return temp;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int contains(T arg0) {
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
	
	public T remove(int pos) {
		if(pos>size)
			throw new IndexOutOfBoundsException(null);
		Iterator<T> it = iterator();
		T curr = null;
		for(int i=0; i<=pos; i++)
		{
			curr = it.next();
		}
		it.remove();
		return curr;
	}

	public void printList() {
		PrintStream ps = new PrintStream(System.out);
		for (T t : this) {
			ps.println(t.toString());
		}
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
