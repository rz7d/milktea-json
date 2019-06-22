package milktea.json.element;

public interface JSONValue {

    <T> T convert(Class<T> type);

}
