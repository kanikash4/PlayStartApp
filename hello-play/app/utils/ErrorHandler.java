import com.fasterxml.jackson.databind.node.ObjectNode;

import exceptions.CustomException;
import play.http.HttpErrorHandler;
import play.libs.Json;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Singleton
public class ErrorHandler implements HttpErrorHandler {
    public CompletionStage<Result> onClientError(RequestHeader request, int statusCode, String message) {
        ObjectNode result = Json.newObject();
        result.put("status", statusCode);
        result.put("message","Incorrect data format");
        return CompletableFuture.completedFuture(Results.status(statusCode, result));
    }

    public CompletionStage<Result> onServerError(RequestHeader request, Throwable exception) {
        ObjectNode result = Json.newObject();
        if(exception instanceof CustomException){
            CustomException e = (CustomException) exception;
            result.put("status", e.getStatus());
            result.put("message", e.getMessage());
            return CompletableFuture.completedFuture(Results.status(e.getStatus(), result));
        }
        else {
            String message = exception.getMessage();
            if(message.equals(null)|| message.isEmpty())
                message="Internal Server error";
            result.put("status", 500);
            result.put("message", message);
            return CompletableFuture.completedFuture(Results.status(500, result));
        }
    }
}