package controllers;

import play.mvc.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Result;

import java.io.IOException;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    public HomeController(){
        super();
    }

    public Result index() {
        return ok();
    }

    public Result welcomeUser(){
        ObjectNode objectNode = Json.newObject();
        objectNode.put("Name","My Name");
        return ok(Json.toJson(objectNode));
    }

    public Result addData() throws IOException {
        ObjectNode objectNode = Json.newObject();
        objectNode.put("message", "data added successfully");
        return play.mvc.Results.ok(objectNode);
    }


}
