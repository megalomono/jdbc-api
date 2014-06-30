package es.teseractos.query.select;

public enum Order {
	ASCENDANT, DESCENDANT;

	protected String sql() {
		switch (this) {
		case ASCENDANT:
			return " ASC";
		case DESCENDANT:
			return " DESC";
		default:
			return "";
		}
	}
}
