package postal_label_generator_service;


import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static postal_label_generator_service.PdfCreator.convertPostalDataToPdf;

/**
 * This class contains the {@link #getPostalData} method for a postal label microservice
 * most practical to utilize for any kind of web store.
 * The idea is when a customer of a webshop places an order the client (the store itself) calls the API
 * with the name and shipping address and immediately gets back a PDF document with the same details in a format
 * convenient to print out and use as a postal label for shipping.
 */

public class PostalLabelServiceController {

    private static PostalLabelServiceController INSTANCE;

    public static PostalLabelServiceController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostalLabelServiceController();
        }
        return INSTANCE;
    }

    private PostalLabelServiceController() {}

    /**
     * Serves a PDF to save containing the text it reads out from the request body.
     * <p>
     * Requests a JSONObject in the body of a POST request that has a corresponding name and shipping address as values
     * to the following keys in that exact order:
     * <p><ul>
     * <li>"name"
     * <li>"country"
     * <li>"city"
     * <li>"address"
     * <li>"zipcode"
     * </ul><p>
     * In case the request's built up according to above rules the method responds with a PDF doc with the received
     * postal data written in it.
     * This app uses the spark web framework to create the API endpoints so the parameters can be found in
     * {@link Request} and {@link Response} respectively.
     *
     * @param request  a spark request object which body should contain the shipping details
     * @param response  a spark response object where the resulting PDF travels
     * @return a status message 200 if the content of the JSON meets the requirements
     * @throws JSONException  JSON in the request body differs from the specification
     * @throws IOException
     * @see <a href="http://sparkjava.com/">Spark</a>
     */

    public int getPostalData(Request request, Response response) throws JSONException, IOException {
        JSONObject json = new JSONObject(request.body());
        List<String> postalData = new ArrayList<>();
        postalData.add(json.getString("name"));
        postalData.add(json.getString("country"));
        postalData.add(json.getString("city"));
        postalData.add(json.getString("address"));
        postalData.add(json.getString("zipcode"));

        String fileName = convertPostalDataToPdf(postalData);
        response.header("Content-Type", "application/pdf");
        response.header("Content-Disposition", "attachment;filename="+ fileName);
        ServletOutputStream outputStream = response.raw().getOutputStream();
        outputStream.write(PdfCreator.convertToBytes(fileName));
        outputStream.flush();
        outputStream.close();
        return 200;
    }

}
