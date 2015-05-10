package aisd.lab3.lista;

class SortedElement<T extends Comparable<T>> 
	implements Comparable<SortedElement<T>> {
	
	private T item;
	private SortedElement<T> next;

	void setNext(SortedElement<T> next) {
		this.next = next;
	}

	SortedElement(T karta, SortedElement<T> next) {
		this.item = karta;
		this.next = next;
	}

	public T getItem() {
		return item;
	}
	

	public SortedElement<T> getNext() {
		return next;
	}

	
	public int compareTo(SortedElement<T> arg0) {
		 return item.compareTo(arg0.getItem());
	}



}
