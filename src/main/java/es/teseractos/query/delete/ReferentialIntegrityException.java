package es.teseractos.query.delete;

@SuppressWarnings("serial")
public class ReferentialIntegrityException extends RuntimeException {

  public ReferentialIntegrityException() {
    super(
        "Failed to delete the tuple: is being referenced by another table");
  }
}
