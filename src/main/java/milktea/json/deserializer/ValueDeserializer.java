package milktea.json.deserializer;

import milktea.json.JSON;
import milktea.json.element.JSONBoolean;
import milktea.json.element.JSONNull;
import milktea.json.element.JSONNumber;
import milktea.json.element.JSONValue;
import milktea.json.immutable.ImmutableJSONNumber;
import milktea.json.standard.RFC8259;

import java.nio.CharBuffer;
import java.util.Objects;

public class ValueDeserializer {

    public static JSONValue deserialize(CharSequence text) {
        Objects.requireNonNull(text);
        return deserialize(CharBuffer.wrap(text));
    }

    public static JSONValue deserialize(CharBuffer buffer) {
        if (!Deserializer.skipWhitespaces(buffer))
            throw Deserializer.newException("Empty root");

        var c = buffer.get(buffer.position());
        switch (c) {
            case JSON.BEGIN_OBJECT_CHAR:
                return ObjectDeserializer.deserialize(buffer);
            case JSON.BEGIN_ARRAY_CHAR:
                return ArrayDeserializer.deserialize(buffer);
            case JSON.QUOTATION_MARK:
                return StringDeserializer.deserialize(buffer);
            case 't':
                return deserializeLiteral(buffer, "true");
            case 'f':
                return deserializeLiteral(buffer, "false");
            case 'n':
                return deserializeLiteral(buffer, "null");
            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return deserializeNumber(buffer);
        }
        throw Deserializer.newException("Invalid token: " + c);
    }

    private static JSONValue deserializeLiteral(CharBuffer buffer, String literal) {
        var len = literal.length();
        for (int i = 0; i < len; ++i) {
            if (!buffer.hasRemaining()) {
                throw Deserializer.newException("Unexpected EOF: " + buffer.rewind().toString());
            }
            var c = buffer.get();
            if (literal.charAt(i) != c)
                throw Deserializer.newException("Invalid literal: " + buffer.position(buffer.position() - 1).toString());
        }

        switch (literal) {
            case "true":
                return JSONBoolean.TRUE;
            case "false":
                return JSONBoolean.FALSE;
            case "null":
                return JSONNull.NULL;
        }
        throw Deserializer.newException("Invalid Literal");
    }

    private static JSONNumber deserializeNumber(CharBuffer buffer) {
        if (!buffer.hasRemaining())
            throw Deserializer.newException("Unexcpeted EOF");
        var sb = new StringBuilder();
        build:
        while (buffer.hasRemaining()) {
            char c = buffer.get();
            switch (c) {
                case JSON.END_ARRAY_CHAR:
                case JSON.END_OBJECT_CHAR:
                case JSON.NAME_SEPARATOR_CHAR:
                case JSON.VALUE_SEPARATOR_CHAR:
                    buffer.position(buffer.position() - 1);
                    break build;
            }
            sb.append(c);
        }

        var text = sb.toString();
        var type = RFC8259.determineNumberType(text);
        switch (type) {
            case INT:
                return ImmutableJSONNumber.of(Integer.parseInt(text));
            case INT_EXP:
            case INT_FRAC:
            case INT_FRAC_EXP:
                return ImmutableJSONNumber.of(Double.parseDouble(text));
            default:
                break;
        }
        throw Deserializer.newException("token {} is not a valid number");
    }

}
