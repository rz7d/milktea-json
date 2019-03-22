package milktea.json;

public class JSONException extends RuntimeException {

  private static final long serialVersionUID = -5520089208780757873L;

  public JSONException() {
    super();
  }

  public JSONException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public JSONException(String message, Throwable cause) {
    super(message, cause);
  }

  public JSONException(String message) {
    super(message);
  }

  public JSONException(Throwable cause) {
    super(cause);
  }

}
