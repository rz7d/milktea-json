package com.github.rz7d.commons.json.serializer;

import com.github.rz7d.commons.json.model.JSONString;
import com.github.rz7d.commons.json.standard.RFC8259;

import java.nio.CharBuffer;

public final class StringSerializer implements Serializer<JSONString> {

    public static void escape(StringBuilder out, CharBuffer in) {
        while (in.hasRemaining()) {
            char ch = in.get();
            switch (ch) {
                case '"':
                    out.append('\\');
                    out.append('"');
                    continue;
                case '\\':
                    out.append('\\');
                    out.append('\\');
                    continue;
                case '/':
                    out.append('\\');
                    out.append('/');
                    continue;
                case '\b':
                    out.append('\\');
                    out.append('b');
                    continue;
                case '\f':
                    out.append('\\');
                    out.append('f');
                    continue;
                case '\n':
                    out.append('\\');
                    out.append('n');
                    continue;
                case '\r':
                    out.append('\\');
                    out.append('r');
                    continue;
                case '\t':
                    out.append('\\');
                    out.append('t');
                    continue;
            }
            if (ch >= 127) {
                out.append('\\').append('u').append(Integer.toString(ch, 16));
                continue;
            }
            out.append(ch);
        }
    }

    @Override
    public void serialize(StringBuilder builder, JSONString value) {
        builder.append(RFC8259.QUOTATION_MARK);
        escape(builder, CharBuffer.wrap(value.value()));
        builder.append(RFC8259.QUOTATION_MARK);
    }

}
