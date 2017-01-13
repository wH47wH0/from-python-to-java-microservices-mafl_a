package postal_label_generator_service;

import java.io.*;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class PdfCreator {

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
