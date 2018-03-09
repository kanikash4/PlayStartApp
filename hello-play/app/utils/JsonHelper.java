package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
    public static boolean requestHasRequiredParameters(JsonNode json, String... requiredParams) {
        for (String param : requiredParams) {
            if (json.get(param) == null || json.get(param).asText().isEmpty()) {
                throw new RuntimeException("Invalid field value in JSON : " + param);
            }
        }
        return true;
    }

    public static boolean validateNumericParameters(JsonNode json, String... requiredParams) {
        for (String param : requiredParams) {
            if (!StringUtils.isNumeric(json.get(param).asText())) {
                throw  new RuntimeException("Invalid field value in JSON ( Non Numeric ) : " + param);
            }
        }
        return true;
    }
    public static String getRequestDataAsText(JsonNode json, String param) {
        return json.get(param) != null ? json.get(param).textValue() : null;
    }

    public static void parameterAreValid(String... parameters) {
        for (String param : parameters) {
            if (param == null || param.isEmpty() || param.equals("null")) {
                throw new RuntimeException("Invalid Parameter value : " + param);
            }
        }
    }

    public static JsonNode getRequestDataAsJsonNode(JsonNode json, String param) {
        return json.get(param) != null ? json.get(param) : null;
    }

    public static List<String> getRequestDataAsList(JsonNode json, String parameters) {

        List<String> list = new ArrayList<String>();
        for (String listItem : list) {
            list.add(listItem);
        }
        return list;
    }

}
