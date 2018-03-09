/*
 * APIResponse
 */
package utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

import static play.mvc.Results.status;

public class APIResponse {
    public static Map<String, String> httpResponseCodes;

    static {
        httpResponseCodes = new HashMap<String, String>();
        httpResponseCodes.put("200", "HTTP 200 - Ok");
        httpResponseCodes.put("201", "HTTP 201 - Resource has been created");
        httpResponseCodes.put("400", "HTTP 400 - Invalid request message");
        httpResponseCodes.put("401", "HTTP 401 - You don't have the necessary credentials.");
        httpResponseCodes.put("403", "HTTP 403 - You don't have permission to access this resource.");
        httpResponseCodes.put("404", "HTTP 404 - Resource not found");
        httpResponseCodes.put("408", "HTTP 408 - Request Timeout");
        httpResponseCodes.put("500", "HTTP 500 - Unable to process this request");
    }

    public static Result okAsJSON() {
        return resultAsJSON("200");
    }

    public static Result createdAsJSON() {
        return resultAsJSON("201");
    }

    public static Result badAsJSON() {
        return resultAsJSON("400");
    }

    public static Result unauthenticatedAsJSON() {
        return resultAsJSON("401");
    }

    public static Result forbiddenAsJSON() {
        return resultAsJSON("403");
    }

    public static Result notFoundAsJSON() {
        return resultAsJSON("404");
    }

    public static Result timeoutAsJSON() {
        return resultAsJSON("408");
    }

    public static Result errorAsJSON() {
        return resultAsJSON("500");
    }

    protected static Result resultAsJSON(String httpResponseCode) {
        ObjectNode result = Json.newObject();
        result.put("status", httpResponseCode);
        result.put("message", httpResponseCodes.get(httpResponseCode));
        return status(Integer.parseInt(httpResponseCode), result);
    }
}
