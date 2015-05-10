package aisd.lab3.lista;

import java.io.PrintStream;
import java.util.Iterator;

public class SortedLinkedList<T extends Comparable<T>> implements Iterable<T> {

	private SortedElement<T> first;
	private int size;

	public SortedLinkedList() {
		first = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public SortedElement<T> getFirst() {
		return first;
	}

	public void add(T arg0) {
		SortedElement<T> temp = new SortedElement<T>(arg0, null);
		if (size == 0)
			addFirst(arg0);
		else {
			boolean inserted = false;
			SortedElement<T> curr = first;
			SortedElement<T> prev = null;
			for (int counter = 0; counter < size && !inserted; counter++) {
				if (curr.compareTo(temp) != -1) {
					if (prev == null) {
						addFirst(arg0);
					} else {
						prev.setNext(temp);
						temp.setNext(curr);
						size++;
					}
					inserted = true;
				}
				prev = curr;
				curr = curr.getNext();
			}
			if (!inserted) {
				prev.setNext(temp);
				temp.setNext(curr);
				size++;
			}

		}
	}

	void addFirst(T arg0) {
		SortedElement<T> temp = new SortedElement<T>(arg0, first);
		first = temp;
		size++;
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
		return new SortedListIterator<T>(this);
	}

	public void removeDuplicats() {
		Iterator<T> it = iterator();
		T curr = it.next();
		T prev = null;
		boolean wasRemoved = false;
		while(it.hasNext())
		{
			if(!wasRemoved)
				prev = curr;
			else
				wasRemoved = false;
			curr = it.next();
			if(prev.compareTo(curr)==0)
			{
				System.out.printf("Usunieto %s%n",prev.toString());
				it.remove();
				wasRemoved = true;
			}
		}
	}

	void decrementSize() {
		size--;
	}

	void setFirst(SortedElement<T> first) {
		this.first = first;
	}

}
