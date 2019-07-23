package milktea.json.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.CharBuffer;

import org.junit.jupiter.api.Test;

public class StringParserTest {

	private static CharBuffer makeInput(String text) {
		return CharBuffer.wrap("\"" + text + "\"");
	}

	@Test
	public void testEscape() {
		assertEquals('\u3042', StringParser.escape(CharBuffer.wrap("u3042")));
		assertEquals('"', StringParser.escape(CharBuffer.wrap("\"")));
		assertEquals('\\', StringParser.escape(CharBuffer.wrap("\\")));
		assertEquals('/', StringParser.escape(CharBuffer.wrap("/")));
		assertEquals('\b', StringParser.escape(CharBuffer.wrap("b")));
		assertEquals('\f', StringParser.escape(CharBuffer.wrap("f")));
		assertEquals('\n', StringParser.escape(CharBuffer.wrap("n")));
		assertEquals('\r', StringParser.escape(CharBuffer.wrap("r")));
		assertEquals('\t', StringParser.escape(CharBuffer.wrap("t")));
		assertEquals('\\', StringParser.escape(CharBuffer.wrap("other")));
	}

	@Test
	public void testDeserialize() {
		assertEquals("\u3042", StringParser.deserialize(makeInput("\\u3042")).toString());
		assertEquals("\"", StringParser.deserialize(makeInput("\\\"")).toString());
		assertEquals("\\", StringParser.deserialize(makeInput("\\\\")).toString());
		assertEquals("/", StringParser.deserialize(makeInput("\\/")).toString());
		assertEquals("\b", StringParser.deserialize(makeInput("\\b")).toString());
		assertEquals("\f", StringParser.deserialize(makeInput("\\f")).toString());
		assertEquals("\n", StringParser.deserialize(makeInput("\\n")).toString());
		assertEquals("\r", StringParser.deserialize(makeInput("\\r")).toString());
		assertEquals("\t", StringParser.deserialize(makeInput("\\t")).toString());
		assertEquals("other", StringParser.deserialize(makeInput("other")).toString());
	}

}
