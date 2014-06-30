package es.teseractos.query.condition;

import java.util.Collection;

import es.teseractos.query.Parameter;

public abstract class Condition implements Parameter {

	public static Condition isNull(String field) {
		return new SimpleCondition(field, Comparison.NULL, null);
	}

	public static Condition isNotNull(String field) {
		return new SimpleCondition(field, Comparison.NOT_NULL, null);
	}

	public static Condition equalThan(String field, Object value) {
		return new SimpleCondition(field, Comparison.EQUAL, value);
	}

	public static Condition greaterThan(String field, Object value) {
		return new SimpleCondition(field, Comparison.GREATER_THAN, value);
	}

	public static Condition greaterEqualThan(String field, Object value) {
		return new SimpleCondition(field, Comparison.GREATER_EQUAL, value);
	}

	public static Condition lesserThan(String field, Object value) {
		return new SimpleCondition(field, Comparison.LESSER_THAN, value);
	}

	public static Condition lesserEqualThan(String field, Object value) {
		return new SimpleCondition(field, Comparison.LESSER_EQUAL, value);
	}

	public static Condition between(String field, Object min, Object max) {
		return and(min != null ? greaterEqualThan(field, min) : null,
				max != null ? lesserEqualThan(field, max) : null);
	}

	public static Condition exclusiveBetween(String field, Object min,
			Object max) {
		return and(min != null ? greaterThan(field, min) : null,
				max != null ? lesserThan(field, max) : null);
	}

	public static Condition contains(String field, Object value) {
		return new SimpleCondition(field, Comparison.CONTAINS, value);
	}

	public static Condition and(Condition... conditions) {
		return compositeCondition(Operator.AND, conditions);
	}

	public static Condition and(Collection<Condition> conditions) {
		if (conditions != null) {
			Condition[] cArray = new Condition[conditions.size()];
			return and(conditions.toArray(cArray));
		}
		return and();
	}

	public static Condition or(Condition... conditions) {
		return compositeCondition(Operator.OR, conditions);
	}

	public static Condition or(Collection<Condition> conditions) {
		if (conditions != null) {
			Condition[] cArray = new Condition[conditions.size()];
			return or(conditions.toArray(cArray));
		}
		return or();
	}

	private static Condition compositeCondition(Operator operator,
			Condition... conditions) {
		try {
			return new CompositeCondition(operator, conditions);
		} catch (EmptyConditionException e) {
			return null;
		}
	}

	protected Condition() {
	}

	public abstract String sql();
}
