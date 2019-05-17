package milktea.json;

import milktea.json.deserializer.ValueDeserializer;
import milktea.json.element.JSONObject;
import milktea.json.element.JSONValue;
import milktea.json.standard.RFC8259;

public final class JSON {

    public static final char BEGIN_ARRAY_CHAR = RFC8259.BEGIN_ARRAY_CHAR;
    public static final char BEGIN_OBJECT_CHAR = RFC8259.BEGIN_OBJECT_CHAR;
    public static final char QUOTATION_MARK = RFC8259.QUOTATION_MARK;
    public static final char END_ARRAY_CHAR = RFC8259.END_ARRAY_CHAR;
    public static final char END_OBJECT_CHAR = RFC8259.END_OBJECT_CHAR;
    public static final char NAME_SEPARATOR_CHAR = RFC8259.NAME_SEPARATOR_CHAR;
    public static final char VALUE_SEPARATOR_CHAR = RFC8259.VALUE_SEPARATOR_CHAR;

    private JSON() {
    }

    public static JSONValue parse(CharSequence text) {
        return ValueDeserializer.deserialize(text);
    }

    public static JSONObject parseObject(CharSequence text) {
        return parse(text).convert(JSONObject.class);
    }

    public static boolean isWhitespace(char ch) {
        return RFC8259.isWhitespace(ch);
    }

    public static boolean isNumber(String expression) {
        return RFC8259.isNumber(expression);
    }

    public static char beginArrayChar() {
        return BEGIN_ARRAY_CHAR;
    }

    public static char beginObjectChar() {
        return BEGIN_OBJECT_CHAR;
    }

    public static char endArrayChar() {
        return END_ARRAY_CHAR;
    }

    public static char endObjectChar() {
        return END_OBJECT_CHAR;
    }

    public static char nameSeparatorChar() {
        return NAME_SEPARATOR_CHAR;
    }

    public static char valueSeparatorChar() {
        return VALUE_SEPARATOR_CHAR;
    }

    public static char quotationMark() {
        return QUOTATION_MARK;
    }

}
