package postal_label_generator_service;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.*;
import java.util.List;

/**
 * This class contains methods responsible for writing <code>String</code>s from a <code>List</code>
 * into a new PDF file and prepare said file for sending on.
 * It's part of a postal label microservice most practical to utilize for any kind of web store.
 * The idea is when a customer of a webshop places an order the client (the store itself) calls the API
 * with the name and shipping address and immediately gets back a PDF document with the same details in a format
 * convenient to print out and use as a postal label for shipping.
 */

public class PdfCreator {

    /**
     * Creates a PDF out of <code>String</code>s in a <code>List</code> it receives.
     * <p>
     * Writes the content of the parameter <code>postalData</code> into a new PDF doc and saves it into
     * the project root dir.
     * The <code>postalData</code> parameter should be a <code>List&lt;String&gt;</code> wherein every element
     * represents one row of text in the PDF doc.
     *
     * @param postalData  a <code>List&lt;String&gt;</code> with the desired text in it
     * @return the name of the newly genderated PDF as a <code>String</code>
     */

    public static String convertPostalDataToPdf(List<String> postalData) {
        try {
            String id = PdfCreator.generateId(postalData);
            String fileName = "PostalLabel" + id + ".pdf"; // name of our file

            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();

            doc.addPage(page);

            PDPageContentStream content = new PDPageContentStream(doc, page);
            for (int i = 0, y_pos = 740; i < 5; i++, y_pos -= 50) {
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
        } catch(IOException | COSVisitorException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    private static String generateId(List<String> listOfStrings) {
        String newID = "";
        for(String elem : listOfStrings) {
            newID = newID.concat(elem.substring(0, 2));
        }
        return newID;
    }

    public static byte[] convertToBytes(String filename) throws FileNotFoundException {
        File file = new File(filename);

        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
//                # of bytes testprint
//                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            System.out.println("Something went wrong, when the program tried to convert pdf to bytearray");
        }
        return bos.toByteArray();

    }
}
