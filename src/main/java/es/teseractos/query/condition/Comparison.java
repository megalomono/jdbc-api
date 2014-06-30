package es.teseractos.query.condition;

public enum Comparison {
	EQUAL, GREATER_THAN, LESSER_THAN, GREATER_EQUAL, LESSER_EQUAL, NULL, NOT_NULL, CONTAINS;

	protected String sql() {
		switch (this) {
		case GREATER_THAN:
			return ">?";
		case GREATER_EQUAL:
			return ">=?";
		case LESSER_THAN:
			return "<?";
		case LESSER_EQUAL:
			return "<=?";
		case EQUAL:
			return "=?";
		case CONTAINS:
			return " MATCHES ?";
		case NOT_NULL:
			return " IS NOT NULL";
		default:
			return " IS NULL";
		}
	}
}
