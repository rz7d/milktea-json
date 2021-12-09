package com.github.rz7d.commons.json.lazy;

import com.github.rz7d.commons.json.model.JSONBoolean;
import com.github.rz7d.commons.json.model.JSONNull;
import com.github.rz7d.commons.json.model.JSONValue;
import com.github.rz7d.commons.json.parser.LiteralParser;
import com.github.rz7d.commons.json.parser.NumberParser;
import com.github.rz7d.commons.json.parser.Parser;
import com.github.rz7d.commons.json.parser.StringParser;
import com.github.rz7d.commons.json.standard.RFC8259;

import java.nio.CharBuffer;

public final class LazyToken extends Lazy<JSONValue> {

    private static void skipWhileInNest(CharBuffer input, char pushMarker, char popMarker) {
        int stack = 1;
        while (input.hasRemaining()) {
            char c = input.get();
            if (c == pushMarker)
                ++stack;
            if (c == popMarker)
                --stack;
            if (stack <= 0)
                break;
        }
    }

    /**
     * @param expression input expression in json subsequence
     * @return is end of object or end of array
     */
    private static boolean skip(CharBuffer expression) {
        switch (expression.get()) {
            case RFC8259.END_OBJECT_CHAR:
            case RFC8259.END_ARRAY_CHAR:
                return true;
            case RFC8259.BEGIN_OBJECT_CHAR:
                skipWhileInNest(expression, RFC8259.BEGIN_OBJECT_CHAR, RFC8259.END_OBJECT_CHAR);
                break;
            case RFC8259.BEGIN_ARRAY_CHAR:
                skipWhileInNest(expression, RFC8259.BEGIN_ARRAY_CHAR, RFC8259.END_ARRAY_CHAR);
                break;
            case RFC8259.QUOTATION_MARK:
                // TODO: Performance
                StringParser.scan(expression);
                break;
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
                expression.position(expression.position() - 1);
                NumberParser.parse(expression);
                break;
            case 't':
                expression.position(expression.position() - 1);
                LiteralParser.parse(expression, "true", JSONBoolean.TRUE);
                break;
            case 'f':
                expression.position(expression.position() - 1);
                LiteralParser.parse(expression, "false", JSONBoolean.FALSE);
                break;
            case 'n':
                expression.position(expression.position() - 1);
                LiteralParser.parse(expression, "null", JSONNull.NULL);
                break;
        }
        return false;
    }

    public static LazyToken create(CharBuffer token) {
        CharBuffer mark = token.duplicate();
        return skip(token) ? null : new LazyToken(mark);
    }

    private LazyToken(CharBuffer token) {
        super(token);
    }

    @Override
    protected JSONValue parse() {
        CharBuffer token = this.token.duplicate();
        char c = token.get(token.position());
        switch (c) {
            case RFC8259.BEGIN_OBJECT_CHAR:
                return LazyJSONObject.parse(token);
            case RFC8259.BEGIN_ARRAY_CHAR:
                return LazyJSONArray.parse(token);
            case RFC8259.QUOTATION_MARK:
                return StringParser.parse(token);
            case 't':
                return LiteralParser.parse(token, "true", JSONBoolean.TRUE);
            case 'f':
                return LiteralParser.parse(token, "false", JSONBoolean.FALSE);
            case 'n':
                return LiteralParser.parse(token, "null", JSONNull.NULL);
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
                return NumberParser.parse(token);
        }
        throw Parser.newException("Invalid token: " + c);
    }

}
