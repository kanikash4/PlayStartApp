package controllers;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import services.ImageRepo;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.APIResponse.errorAsJSON;
import static utils.APIResponse.okAsJSON;
import static utils.JsonHelper.parameterAreValid;

public class ImageController  {

    Logger logger = LoggerFactory.getLogger(ImageController.class);
    private ImageRepo imageServices;
    private final FormFactory formFactory;

    @Inject
    public ImageController(ImageRepo imageServices,final FormFactory formFactory) {
        super();
        this.imageServices = imageServices;
        this.formFactory = formFactory;
    }

}
