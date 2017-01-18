package postal_label_generator_service;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * This class contains the main method which purpose is to set up a Spark web server as the one API endpoint for a
 * postal label microservice most practical to utilize for any kind of web store.
 * The idea is when a customer of a webshop places an order the client (the store itself) calls the API
 * with the name and shipping address and immediately gets back a PDF document with the same details in a format
 * convenient to print out and use as a postal label for shipping.
 *
 * @see <a href="http://sparkjava.com/">Spark</a>
 */

public class PostalLabelGeneratorService {
    public static void main(String[] args) {
        post("/", PostalLabelServiceController.getInstance()::getPostalData);
        enableDebugScreen();
    }
}
