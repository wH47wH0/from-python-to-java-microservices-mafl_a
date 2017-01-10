package postal_label_generator_service;


import org.json.JSONException;
import org.json.JSONObject;
import spark.Response;
import spark.Request;
import spark.Response;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class PostalLabelServiceController {

    private static PostalLabelServiceController INSTANCE;

    public static PostalLabelServiceController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostalLabelServiceController();
        }
        return INSTANCE;
    }

    private PostalLabelServiceController() {}

    public String getPostalData(Request request, Response response) throws JSONException, FileNotFoundException {
        String body = request.body();
        JSONObject json = new JSONObject(response.body());
        List<String> postalData = new ArrayList<>();
        postalData.add(json.getString("name"));
        postalData.add(json.getString("country"));
        postalData.add(json.getString("city"));
        postalData.add(json.getString("address"));
        postalData.add(json.getString("zipcode"));

        response.header("Content-Type", "application/pdf");
        String fileName = PdfCreator.convertPostalDataToPdf(postalData);
        return PdfCreator.convertToBytes(fileName);
    }

}
