package milktea.json.immutable;

import milktea.json.element.JSONBoolean;

public class ImmutableJSONBoolean implements JSONBoolean {

    private static final ImmutableJSONBoolean TRUE = new ImmutableJSONBoolean();
    private static final ImmutableJSONBoolean FALSE = new ImmutableJSONBoolean();

    public static JSONBoolean of(boolean value) {
        return value ? TRUE : FALSE;
    }

    @Override
    public boolean value() {
        return this == TRUE;
    }

}
