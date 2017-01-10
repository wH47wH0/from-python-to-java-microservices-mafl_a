package postal_label_generator_service;

import java.io.*;
import java.util.List;
import java.util.UUID;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


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

    public static String convertToBytes(String filename) throws FileNotFoundException {
        File file = new File(filename);

        FileInputStream fis = new FileInputStream(file);
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            System.out.println("jaj");
        }
        byte[] bytes = bos.toByteArray();
        return new String(bytes);
    }
}
