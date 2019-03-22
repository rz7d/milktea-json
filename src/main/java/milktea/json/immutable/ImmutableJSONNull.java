package milktea.json.immutable;

import milktea.json.element.JSONNull;

public class ImmutableJSONNull implements JSONNull {

  public static final ImmutableJSONNull NULL = new ImmutableJSONNull();

  private ImmutableJSONNull() {}

}
