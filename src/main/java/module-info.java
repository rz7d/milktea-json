/**
 * @author azure
 *
 */
module milktea.json {
  exports milktea.json;
  exports milktea.json.standard;
  exports milktea.json.element;
  exports milktea.json.deserializer;
  exports milktea.json.immutable;

  requires fastjson;
  requires java.sql;
}