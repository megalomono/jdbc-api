package es.teseractos.query;

@SuppressWarnings("serial")
public class QueryErrorException extends RuntimeException {

  public QueryErrorException(Throwable cause) {
    super("Failed to generate the query", cause);
  }
}
