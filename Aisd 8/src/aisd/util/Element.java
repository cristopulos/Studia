package aisd.util;

public class Element<T> {

	private T item;
	private Element<T> next;

	public void setNext(Element<T> next) {
		this.next = next;
	}

	public Element(T karta, Element<T> next) {
		this.item = karta;
		this.next = next;
	}

	public T getItem() {
		return item;
	}

	public Element<T> getNext() {
		return next;
	}


}
