package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Controller;

import static utils.Response.badAsJSON;
import static  utils.JsonHelper.requestHasRequiredParameters;

public class ContactsController extends Controller {

    Logger logger = LoggerFactory.getLogger(ContactsController.class);


    public play.mvc.Result addContact () {
        JsonNode request = request().body().asJson();
        if (request == null) {
            return badAsJSON("error occured");

        }
        if(!requestHasRequiredParameters(request, "name", "contactNumber")) {
            logger.error("The request body does not have name or contact number");
            return badAsJSON("The request body does not have name or contact number");
        } else {
            //TODO: business logic
            return null;
        }

    }

}
