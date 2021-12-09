package com.github.rz7d.commons.json;

import com.github.rz7d.commons.json.model.JSONObject;
import org.junit.jupiter.api.Test;

public class Example {

    @Test
    public void example() {
        JSONObject object = JSON.parse("{\"name\":\"John\", \"age\":30, \"car\":null}").asObject();
        System.out.println(object.getString("name").value()); // John
        System.out.println(object.getNumber("age").value()); // 30
        System.out.println(object.get("car")); // null
    }

}
