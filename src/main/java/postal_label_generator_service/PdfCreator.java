package postal_label_generator_service;

import java.util.List;
import java.io.IOException;
import java.util.UUID;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Created by kekesaron on 2017.01.10..
 */
public class PdfCreator {
    public static String convertPostalDataToPdf(List<String> postalData){
        try{
            UUID id = UUID.randomUUID();
            String fileName = "PostalLabel" + id + ".pdf"; // name of our file

            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();

            doc.addPage(page);

            PDPageContentStream content = new PDPageContentStream(doc, page);
            for (int i=0,y_pos=740; i<5; i++,y_pos-=50) {
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 40);
                content.moveTextPositionByAmount(80, y_pos);
                content.drawString(postalData.get(i));
                content.endText();
            }

            content.close();
            doc.save(fileName);
            doc.close();
            return fileName;
        }
        catch(IOException | COSVisitorException e){
            System.out.println(e.getMessage());
        }
        return "";
    }
}
