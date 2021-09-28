package com.github.rz7d.commons.json.lazy.parser;

import com.github.rz7d.commons.json.lazy.LazyJSONArray;
import com.github.rz7d.commons.json.lazy.LazyJSONObject;
import com.github.rz7d.commons.json.lazy.LazyJSONString;
import com.github.rz7d.commons.json.model.JSONBoolean;
import com.github.rz7d.commons.json.model.JSONNull;
import com.github.rz7d.commons.json.model.JSONValue;
import com.github.rz7d.commons.json.parser.LiteralParser;
import com.github.rz7d.commons.json.parser.NumberParser;
import com.github.rz7d.commons.json.parser.Parser;
import com.github.rz7d.commons.json.parser.StringParser;
import com.github.rz7d.commons.json.standard.RFC8259;

import java.nio.CharBuffer;

public final class ValueToken extends Token<JSONValue> {

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
            case '}':
            case ']':
                return true;
            case '{':
                skipWhileInNest(expression, '{', '}');
                break;
            case '[':
                skipWhileInNest(expression, '[', ']');
                break;
            case '"':
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
                LiteralParser.parse(expression, "true", JSONBoolean.TRUE);
                break;
            case 'f':
                LiteralParser.parse(expression, "false", JSONBoolean.FALSE);
                break;
            case 'n':
                LiteralParser.parse(expression, "null", JSONNull.NULL);
                break;
        }
        return false;
    }

    public static ValueToken create(CharBuffer token) {
        ValueToken t = new ValueToken(token.duplicate());
        // TODO: Refactor this
        return skip(token) ? null : t;
    }

    private ValueToken(CharBuffer token) {
        super(token);
    }

    @Override
    protected JSONValue parse() {
        CharBuffer token = this.token.duplicate();
        char c = token.get(token.position());
        switch (c) {
            case RFC8259.BEGIN_OBJECT_CHAR:
                token.get(); // FIXME for LazyObjectParser Bug
                return new LazyJSONObject(LazyObjectParser.scan(token));
            case RFC8259.BEGIN_ARRAY_CHAR:
                return new LazyJSONArray(LazyArrayParser.scan(token));
            case RFC8259.QUOTATION_MARK:
                return new LazyJSONString(new StringToken(token));
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
