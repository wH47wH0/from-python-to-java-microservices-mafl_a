package postal_label_generator_service;


import org.json.JSONException;
import org.json.JSONObject;
import spark.Response;
import spark.Request;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static postal_label_generator_service.PdfCreator.convertPostalDataToPdf;


public class PostalLabelServiceController {

    private static PostalLabelServiceController INSTANCE;

    public static PostalLabelServiceController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostalLabelServiceController();
        }
        return INSTANCE;
    }

    private PostalLabelServiceController() {}

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
