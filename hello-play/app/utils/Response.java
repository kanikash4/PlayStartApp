package utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Result;

import static play.mvc.Results.status;

public class Response {

	public static Result okAsJSON(String msg) { return resultAsJSON("200",msg); }

	public static Result createdAsJSON(String msg) { return resultAsJSON("201",msg); }

	public static Result badAsJSON(String msg) {
		return resultAsJSON("400",msg);
	}

	public static Result unauthenticatedAsJSON(String msg) {
		return resultAsJSON("401",msg);
	}

	public static Result forbiddenAsJSON(String msg) {
		return resultAsJSON("403",msg);
	}

	public static Result notFoundAsJSON(String msg) {
		return resultAsJSON("404",msg);
	}

	public static Result timeoutAsJSON(String msg) {
		return resultAsJSON("408",msg);
	}

	public static Result errorAsJSON(String msg) {
		return resultAsJSON("500",msg);
	}


	protected static Result resultAsJSON(String responseCode, String message) {
		ObjectNode result = Json.newObject();
		//result.put("status", responseCode);
		result.put("message", message);
		return status(Integer.parseInt(responseCode), result);
	}
}
