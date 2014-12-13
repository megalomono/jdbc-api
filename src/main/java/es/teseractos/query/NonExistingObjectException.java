package es.teseractos.query;

@SuppressWarnings("serial")
public class NonExistingObjectException extends RuntimeException {

  public NonExistingObjectException() {
    super("The object don't exists in database");
  }

  public NonExistingObjectException(Throwable cause) {
    super("The object don't exists in database", cause);
  }
}
