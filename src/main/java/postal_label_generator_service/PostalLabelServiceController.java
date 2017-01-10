package postal_label_generator_service;

import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kekesaron on 2017.01.10..
 */
public class PostalLabelServiceController {
    public void getPostalData(Request request, Response response) throws JSONException {
        JSONObject json = new JSONObject(request.body().toString());
        List<String> postalData = new ArrayList<>();
        postalData.add(json.getString("name"));
        postalData.add(json.getString("country"));
        postalData.add(json.getString("city"));
        postalData.add(json.getString("address"));
        postalData.add(json.getString("zipcode"));
//        convertPostalDataToPdf(postalData);



    }
}
