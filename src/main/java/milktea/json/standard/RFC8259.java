package milktea.json.standard;

public final class RFC8259 {

    public static final char BEGIN_ARRAY_CHAR = '[';
    public static final char BEGIN_OBJECT_CHAR = '{';
    public static final char END_ARRAY_CHAR = ']';
    public static final char END_OBJECT_CHAR = '}';
    public static final char NAME_SEPARATOR_CHAR = ':';
    public static final char VALUE_SEPARATOR_CHAR = ',';
    public static final char QUOTATION_MARK = '"';

    private RFC8259() {
    }

    public static boolean isWhitespace(char ch) {
        return ch == 0x20 || ch == 0x09 || ch == 0x0A || ch == 0x0D;
    }

    public enum NumberType {
        INT, INT_FRAC, INT_EXP, INT_FRAC_EXP, NOT_A_NUMBER
    }

    public static boolean isNumber(String expression) {
        return determineNumberType(expression) != NumberType.NOT_A_NUMBER;
    }

    public static NumberType determineNumberType(String expression) {
        final var length = expression.length();
        if (length == 0)
            return NumberType.NOT_A_NUMBER;

        // RFC8259: number = [ minus ] int [ frac ] [ exp ]
        boolean inInt = true;
        boolean inFrac = false;
        boolean inExp = false;

        int local = 0;

        for (var i = 0; i < length; ++i) {
            var ch = expression.charAt(i);

            //
            if (inInt && (!inFrac) && (!inExp)) {
                // RFC8259: minus check
                if (local == 0 && ch == '-')
                    continue;
                if (local == 0 && ch == '0') {
                    if (length == 1)
                        return NumberType.INT;
                    switch (expression.charAt(++i)) {
                        case '.':
                            inFrac = true;
                            local = 0;
                            continue;
                        case 'e':
                            inExp = true;
                            local = 0;
                            continue;
                    }
                    return NumberType.NOT_A_NUMBER;
                }
                ++local;
                switch (ch) {
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
                        continue;
                    case '.':
                        if (local == 0)
                            return NumberType.NOT_A_NUMBER;
                        local = 0;
                        inFrac = true;
                        continue;
                    case 'e':
                        if (local == 0)
                            return NumberType.NOT_A_NUMBER;
                        local = 0;
                        inExp = true;
                        continue;
                    default:
                        return NumberType.NOT_A_NUMBER;
                }
            }

            if (inFrac && (!inExp)) {
                switch (ch) {
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
                        continue;
                    case 'e':
                        local = 0;
                        inExp = true;
                        continue;
                    default:
                        return NumberType.NOT_A_NUMBER;
                }
            }

            if (inExp) {
                switch (ch) {
                    case '+':
                    case '-':
                        if (local != 0)
                            return NumberType.NOT_A_NUMBER;
                        // $FALL-THROUGH$
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
                        ++local;
                        continue;
                    default:
                        return NumberType.NOT_A_NUMBER;
                }
            }
        }
        return inFrac && inExp ? NumberType.INT_FRAC_EXP
            : inExp ? NumberType.INT_EXP : inFrac ? NumberType.INT_FRAC : inInt ? NumberType.INT : NumberType.NOT_A_NUMBER;
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
