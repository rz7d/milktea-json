package com.github.rz7d.commons.json;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.BiConsumer;

public class JSONSerializationTest {

    private static void test(String expression, BiConsumer<String, String> test) {
        test.accept(expression, JSON.serialize(JSON.parse(expression)));
    }

    @Test
    public void serializeNull() {
        test("null", Assertions::assertEquals);
    }

    @Test
    public void serializeObject() {
        test("{}", Assertions::assertEquals);
        test("{\"a\":\"a\",\"b\":1}", Assertions::assertEquals);
    }

    @Test
    public void serializeArray() {
        test("[]", Assertions::assertEquals);
        test("[1,2,3,4]", Assertions::assertEquals);
        test("[\"a\",1,\"b\",2]", Assertions::assertEquals);
        test("[[]]", Assertions::assertEquals);
        test("[{}]", Assertions::assertEquals);
    }

    @Test
    public void serializeNumber() {
        test("334", Assertions::assertEquals);
    }

    @Test
    public void serializeString() {
        // FIXME: XXX: 常にエスケープされる挙動を configurable にする
        test("\"Hello あ\"", Assertions::assertNotEquals);
        test("\"Hello \\u3042\\u3042\\u3042\\u3042\"", Assertions::assertEquals);
    }

    @Test
    public void serializeBoolean() {
        test("false", Assertions::assertEquals);
        test("true", Assertions::assertEquals);
    }

}
