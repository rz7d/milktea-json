package milktea.json.immutable;

import milktea.json.element.JSONNumber;

public class ImmutableJSONNumber implements JSONNumber {

  public static ImmutableJSONNumber of(Number value) {
    return new ImmutableJSONNumber(value);
  }

  private final Number value;

  private ImmutableJSONNumber(Number value) {
    this.value = value;
  }

  @Override
  public Number value() {
    return value;
  }

}
