package aisd4.queue;

public interface Queue<T> {
	public void insert(T item);
	public T remove();
	public boolean isEmpty();
	public boolean isFull();
}
