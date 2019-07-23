package milktea.json.parser;

import milktea.json.JSON;
import milktea.json.element.JSONString;
import milktea.json.immutable.ImmutableJSONString;

import java.nio.CharBuffer;

class StringParser {

	public static JSONString deserialize(CharBuffer buffer) {
		if (buffer.get() != JSON.QUOTATION_MARK)
			throw Parser.newException("Invalid Character: " + buffer.get(buffer.position() - 1));

		var builder = new StringBuilder();
		while (buffer.hasRemaining()) {
			var c = buffer.get();
			switch (c) {
			case JSON.QUOTATION_MARK:
				return ImmutableJSONString.of(builder.toString());
			case '\\':
				c = escape(buffer);
				// $FALL-THROUGH$
			default:
				builder.append(c);
			}
		}
		throw Parser.newException("Unexpected end of token");
	}

	public static char escape(CharBuffer buffer) {
		if (!buffer.hasRemaining())
			throw new IllegalStateException("Unexpected EOF");
		char ch = buffer.get();
		switch (ch) {
		case '"':
			return '"';
		case '\\':
			return '\\';
		case '/':
			return '/';
		case 'b':
			return '\b';
		case 'f':
			return '\f';
		case 'n':
			return '\n';
		case 'r':
			return '\r';
		case 't':
			return '\t';
		case 'u':
			char[] code = new char[4];
			buffer.get(code);
			return (char) Integer.parseInt(String.valueOf(code), 16);
		}
		return '\\';
	}

}
