package xmlTest.test;

public interface LineCallback<T> {
	T doSomethingWithLine(String line, T res);
}
