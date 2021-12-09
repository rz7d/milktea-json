# Milktea JSON

Under constructing

## Usage

```Java
JSONObject object = JSON.parse("""{"name":"John", "age":30, "car":null}""").asObject();
System.out.println(object.getString("name").value()); // John
System.out.println(object.getNumber("age").value()); // 30
System.out.println(object.get("car")); // null
```

