package postal_label_generator_service;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class PdfCreatorTest {

    @Test
    public void convertPostalDataToPdf() {
        ArrayList<String> data = new ArrayList<>();
        data.add("Szerep Elek");
        data.add("Hungary");
        data.add("Budapest");
        data.add("Nagymező utca 5.");
        data.add("1000");
        assertEquals("PostalLabelSzHuBuNa10.pdf", PdfCreator.convertPostalDataToPdf(data));
    }

    @Test
    public void convertToBytes() throws Exception {

    }

}