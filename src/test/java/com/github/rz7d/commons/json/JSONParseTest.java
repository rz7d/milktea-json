package com.github.rz7d.commons.json;

import com.github.rz7d.commons.json.model.*;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class JSONParseTest {

    @SuppressWarnings("unchecked")
    private static <T extends JSONValue> void parseThen(String expression, Class<T> type, Consumer<T> action) {
        JSONValue parsed = JSON.parse(expression);
        assertInstanceOf(type, parsed);
        action.accept((T) parsed);
    }

    @Test
    public void testParseNull() {
        parseThen("null", JSONNull.class, nil -> {
        });
    }

    @Test
    public void testParseBoolean() {
        parseThen("true", JSONBoolean.class, bool -> assertTrue(bool.boolValue()));
        parseThen("false", JSONBoolean.class, bool -> assertFalse(bool.boolValue()));
    }

    @Test
    public void testParseNumber() {
        parseThen("2",
            JSONNumber.class,
            number -> assertEquals(2, number.value()));
    }

    @Test
    public void testParseString() {
        parseThen("\"Hello \\u3042\\u3042\\u3042\\u3042\"",
            JSONString.class,
            string -> assertEquals("Hello \u3042\u3042\u3042\u3042", string.value()));
    }

    @Test
    public void testParseEmptyObject() {
        parseThen("{}",
            JSONObject.class,
            object -> assertEquals(0, object.size()));
    }

    @Test
    public void testParseNestedArrayObject() {
        parseThen("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[334334334]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]",
            JSONArray.class,
            array -> assertEquals(1, array.size()));
    }

    @Test
    public void testParseObject() {
        parseThen("{\"property1\": {}, \"property2\": [], \"property3\": 334, \"property4\": {\"innerProperty1\": 1337}}",
            JSONObject.class,
            object -> {
                assertEquals(4, object.size());
                assertEquals(0, object.getObject("property1").size());
                assertEquals(0, object.getArray("property2").size());
                assertEquals(334, object.getNumber("property3").value().intValue());
                assertEquals(1337, object.getObject("property4").getNumber("innerProperty1").value().intValue());
            });

        parseThen("{\"nested\":null}", JSONObject.class, obj -> {
            assertInstanceOf(JSONNull.class, obj.get("nested"));
        });
        parseThen("{\"nestedTrue\":true,\"nestedFalse\":false}", JSONObject.class, obj -> {
            assertInstanceOf(JSONBoolean.class, obj.get("nestedTrue"));
            assertInstanceOf(JSONBoolean.class, obj.get("nestedFalse"));
        });
        parseThen("{\"nestedNumber\":2}", JSONObject.class, obj -> {
            assertInstanceOf(JSONNumber.class, obj.get("nestedNumber"));
            assertEquals(2, obj.getNumber("nestedNumber").value().intValue());
        });
        parseThen("{\"nestedString\":\"Hello \\u3042\\u3042\\u3042\\u3042\"}", JSONObject.class, obj -> {
            assertInstanceOf(JSONString.class, obj.get("nestedString"));
            assertEquals("Hello \u3042\u3042\u3042\u3042", obj.getString("nestedString").value());
        });
    }

}
