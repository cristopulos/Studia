package aisd.util;

public class Stos<T> extends List<T>  implements Stack<T> {

	@Override
	public boolean empty() {
		return super.isEmpty();
	}

	@Override
	public Element<T> push(T arg0) {
		return super.add(arg0);
	}

	@Override
	public T pop() {
		return super.removeFirst();
	}

	@Override
	public T peek() {
		return super.getFirst().getItem();
	}
	


}
