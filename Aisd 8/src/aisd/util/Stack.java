package aisd.util;

public interface Stack<T> {

	public boolean empty();
	public Element<T> push(T arg0);
	public T pop();
	public T peek();
}
