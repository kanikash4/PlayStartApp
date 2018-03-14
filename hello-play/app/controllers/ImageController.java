package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.DynamicForm;
import play.data.FormFactory;
import services.ImageRepo;

import javax.inject.Inject;

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
