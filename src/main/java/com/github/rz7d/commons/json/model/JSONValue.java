package com.github.rz7d.commons.json.model;

public interface JSONValue {

    default JSONArray asArray() {
        return this instanceof JSONArray ? (JSONArray) this : null;
    }

    default JSONBoolean asBoolean() {
        return this instanceof JSONBoolean ? (JSONBoolean) this : null;
    }

    default JSONNull asNull() {
        return this instanceof JSONNull ? (JSONNull) this : null;
    }

    default JSONNumber asNumber() {
        return this instanceof JSONNumber ? (JSONNumber) this : null;
    }

    default JSONObject asObject() {
        return this instanceof JSONObject ? (JSONObject) this : null;
    }

    default JSONString asString() {
        return this instanceof JSONString ? (JSONString) this : null;
    }

}
