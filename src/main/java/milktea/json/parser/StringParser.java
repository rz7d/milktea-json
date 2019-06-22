package milktea.json.parser;

import milktea.json.JSON;
import milktea.json.element.JSONString;
import milktea.json.immutable.ImmutableJSONString;

import java.nio.CharBuffer;

class StringParser {

    private static final int[] TABLE = {
            // @formatter:off
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            +0, +1, +2, +3, +4, +5, +6, +7, +8, +9, -0, -0, -0, -0, -0, -0,
            -0, 10, 11, 12, 13, 14, 15, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, 10, 11, 12, 13, 14, 15, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0,
            -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0, -0
            // @formatter:on
    };

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
            var code = 0;
            for (int i = 0; i < 4; ++i) {
                if (!buffer.hasRemaining()) {
                    break;
                }
                code <<= 4;
                code += TABLE[buffer.get()];
            }
            return (char) code;
        }
        return '\\';
    }

}
