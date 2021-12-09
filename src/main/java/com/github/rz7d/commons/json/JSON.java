package com.github.rz7d.commons.json;

import com.github.rz7d.commons.json.model.JSONValue;
import com.github.rz7d.commons.json.parser.ValueParser;
import com.github.rz7d.commons.json.serializer.ValueSerializer;

/**
 * JSON 形式の文字列と JSON オブジェクトの総合変換を行う動作を定義したクラス。
 */
public final class JSON {

    /**
     * 指定された文字列を解析し、Java オブジェクトに変換された結果 (単一の JSON 値) を表す JSONValue を返します。
     *
     * @param json JSON 形式として有効な文字列
     * @return 変換された結果 (単一の JSON 値) を表す、JSONValue
     * @throws JSONParseException 指定された文字列が有効な JSON 形式でない場合
     */
    public static JSONValue parse(String json) {
        return ValueParser.deserialize(json);
    }

    /**
     * JSONValue を有効な JSON 形式の文字列にシリアライズします。
     *
     * @param value シリアライズする JSON 値を表す JSONValue
     * @return 有効な JSON 形式の文字列
     */
    public static String serialize(JSONValue value) {
        return ValueSerializer.serialize(value);
    }

    private JSON() {
    }

}
