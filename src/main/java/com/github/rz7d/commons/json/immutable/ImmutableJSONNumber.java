package com.github.rz7d.commons.json.immutable;

import com.github.rz7d.commons.json.model.JSONNumber;

public final class ImmutableJSONNumber extends ImmutableJSONValue implements JSONNumber {

    private final Number value;

    private ImmutableJSONNumber(Number value) {
        this.value = value;
    }

    public static ImmutableJSONNumber of(Number value) {
        return new ImmutableJSONNumber(value);
    }

    @Override
    public Number value() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ImmutableJSONNumber other = (ImmutableJSONNumber) obj;
        if (this.value == null) {
            if (other.value != null)
                return false;
        } else if (!this.value.equals(other.value))
            return false;
        return true;
    }

}
