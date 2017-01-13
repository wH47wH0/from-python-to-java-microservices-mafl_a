package postal_label_generator_service;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by kekesaron on 2017.01.10..
 */
public class PostalLabelGeneratorService {
    public static void main(String[] args) {
        post("/", PostalLabelServiceController.getInstance()::getPostalData);
        enableDebugScreen();
    }
}
