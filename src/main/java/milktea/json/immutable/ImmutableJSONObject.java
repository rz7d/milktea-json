package milktea.json.immutable;

import java.util.Map;

import milktea.json.element.JSONObject;
import milktea.json.element.JSONValue;

public class ImmutableJSONObject implements JSONObject {

  private final Map<String, JSONValue> map;

  public ImmutableJSONObject(Map<String, JSONValue> map) {
    this.map = map;
  }

  @Override
  public JSONValue get(String name) {
    return map.get(name);
  }

}
