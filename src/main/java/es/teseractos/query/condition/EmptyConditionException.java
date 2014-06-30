package es.teseractos.query.condition;

@SuppressWarnings("serial")
public class EmptyConditionException extends Exception {

	public EmptyConditionException() {
		super("The condition has no assigned value");
	}
}
