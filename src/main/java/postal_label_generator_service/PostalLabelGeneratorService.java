package postal_label_generator_service;

import static spark.Spark.*;

/**
 * Created by kekesaron on 2017.01.10..
 */
public class PostalLabelGeneratorService {
    public static void main(String[] args) {
        get("/", (request, response) -> {
            response.redirect("http://images-cdn.9gag.com/photo/24566_700b.jpg");
            return null;
        });
    }
}
