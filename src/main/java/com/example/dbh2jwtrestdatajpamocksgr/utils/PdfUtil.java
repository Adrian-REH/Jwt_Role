package com.example.dbh2jwtrestdatajpamocksgr.utils;

        import com.itextpdf.io.image.ImageData;
        import com.itextpdf.io.image.ImageDataFactory;
        import com.itextpdf.kernel.colors.ColorConstants;
        import com.itextpdf.kernel.colors.DeviceRgb;
        import com.itextpdf.kernel.geom.PageSize;
        import com.itextpdf.kernel.pdf.PdfDocument;
        import com.itextpdf.kernel.pdf.PdfWriter;
        import com.itextpdf.layout.Document;
        import com.itextpdf.layout.borders.Border;
        import com.itextpdf.layout.borders.SolidBorder;
        import com.itextpdf.layout.element.*;
        import com.itextpdf.layout.properties.HorizontalAlignment;
        import com.itextpdf.layout.properties.TextAlignment;
        import com.itextpdf.layout.properties.VerticalAlignment;
        import org.springframework.core.io.ByteArrayResource;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.math.BigDecimal;
        import java.math.RoundingMode;
        import java.util.Date;

        import static org.apache.el.lang.ELArithmetic.multiply;

/**
 * @author Jonathan Giovanni Hernandez
 * @company Finansoportes Consulting
 * @created 21/09/2022
 */
public class PdfUtil {

    public static void removeBorder(Table table)
    {
        for (IElement iElement : table.getChildren()) {
            ((Cell)iElement).setBorder(Border.NO_BORDER);
        }
    }

    public static ByteArrayOutputStream  generateInvoice()  {


        var out = new ByteArrayOutputStream();
        var pdfWriter = new PdfWriter(out);
        var pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        pdfDocument.addNewPage();


        var backgroundBlueColor = new DeviceRgb(63, 169, 219);

        var document = new Document(pdfDocument);

        /** header **/

        var headerTable = new Table(new float[] {280, 280});

        //headerTable.setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(ColorConstants.WHITE);

     /*   ImageData imageData = ImageDataFactory.create("classpath:logo_finocio.png");
        Image pdfImg = new Image(imageData);
*/
        headerTable.addCell(new Cell().add(new Paragraph(
                "IBIOCD".toUpperCase()).setFontSize(12f).setTextAlignment(TextAlignment.LEFT)
                .add(new Paragraph("CIF:"+ "998828312"+
                        "\n"+"Cichabamba 1256, Madrid"+
                        "\nTEL: "+"+546666154"+
                        "\n"+"adrianherrera@gmail.com")
                        .setFontSize(10f).setTextAlignment(TextAlignment.LEFT)))
        );

        Paragraph paragraph=new Paragraph("");
/*        pdfImg.setWidth(250);
        pdfImg.setHeight(79);
        //Add Image to Paragraph
        paragraph.add(pdfImg);*/
        paragraph.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        paragraph.setVerticalAlignment(VerticalAlignment.MIDDLE);
        paragraph.setTextAlignment(TextAlignment.RIGHT);
        paragraph.setPadding(0f);
        paragraph.setMargin(0f);
        headerTable.addCell(new Cell().add(paragraph));

        PdfUtil.removeBorder(headerTable);
        document.add(headerTable);


        /** receipt header **/
        document.add(new Paragraph(""));

        var headerReceiptTable = new Table(new float[] {280, 280});



        headerReceiptTable.setBackgroundColor(backgroundBlueColor).setFontColor(ColorConstants.WHITE);

        var receiptDate = new Date();

        headerReceiptTable.addCell(new Cell().add(new Paragraph("FACTURA VENTA: "+"Identifier dds").setTextAlignment(TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(12)));
        headerReceiptTable.addCell(new Cell().add(new Paragraph("FECHA: "+receiptDate).setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(12)));

        PdfUtil.removeBorder(headerReceiptTable);
        document.add(headerReceiptTable);

        /** receipt info **/

        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        float infoFont = 9f;
        var infoTable = new Table(new float[] {100,180});
        infoTable.setMaxWidth(280);

        infoTable.addCell(new Cell(0, 4).add(new Paragraph("FACTURAR A").setFontSize(10f).setFontColor(backgroundBlueColor)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));

        infoTable.addCell(new Cell().add(new Paragraph("EMPRESA:").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
        infoTable.addCell(new Cell().add(new Paragraph("IBIOCD".toUpperCase()).setFontSize(infoFont)).setBorder(Border.NO_BORDER));

        infoTable.addCell(new Cell().add(new Paragraph("CIF:").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
        infoTable.addCell(new Cell().add(new Paragraph("998828312").setFontSize(infoFont)).setBorder(Border.NO_BORDER));

        infoTable.addCell(new Cell().add(new Paragraph("DIRECCIÓN:").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
        infoTable.addCell(new Cell().add(new Paragraph("Cochabamba 1256").setFontSize(infoFont)).setBorder(Border.NO_BORDER));

        infoTable.addCell(new Cell().add(new Paragraph("TELÉFONO:").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
        infoTable.addCell(new Cell().add(new Paragraph("+54666616554").setFontSize(infoFont)).setBorder(Border.NO_BORDER));

        document.add(infoTable);

        /** receipt details **/
        float dataFont = 9f;
        float minHeight = dataFont * 2;
        var dataTable = new Table(new float[] {60, 200,100,100,100});
        dataTable.setMarginTop(10f);


        dataTable.addHeaderCell(new Cell().add(new Paragraph("CANTIDAD").setFontSize(10f).setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(backgroundBlueColor).setFontColor(ColorConstants.WHITE).setBorder(Border.NO_BORDER));
        dataTable.addHeaderCell(new Cell().add(new Paragraph("DESCRIPCIÓN").setFontSize(10f).setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(backgroundBlueColor).setFontColor(ColorConstants.WHITE).setBorder(Border.NO_BORDER));
        dataTable.addHeaderCell(new Cell().add(new Paragraph("PRECIO UNITARIO").setFontSize(10f).setTextAlignment(TextAlignment.RIGHT)).setBackgroundColor(backgroundBlueColor).setFontColor(ColorConstants.WHITE).setBorder(Border.NO_BORDER));
        dataTable.addHeaderCell(new Cell().add(new Paragraph("DESCUENTO").setFontSize(10f).setTextAlignment(TextAlignment.RIGHT)).setBackgroundColor(backgroundBlueColor).setFontColor(ColorConstants.WHITE).setBorder(Border.NO_BORDER));
        dataTable.addHeaderCell(new Cell().add(new Paragraph("TOTAL").setFontSize(10f).setTextAlignment(TextAlignment.RIGHT)).setBackgroundColor(backgroundBlueColor).setFontColor(ColorConstants.WHITE).setBorder(Border.NO_BORDER));





        dataTable.addCell(new Cell().add(new Paragraph("1").setFontSize(dataFont).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("LICENCIA DE SOFTWARE "+123+" DE "+233+" "+12+" PARA ATM: "+"IB-3E3").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph(1+" EUR").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph(23+" ("+0.2+" %)  EUR").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph(24+" EUR").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));

        if(false){
            dataTable.addCell(new Cell().add(new Paragraph("1").setFontSize(dataFont).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
            dataTable.addCell(new Cell().add(new Paragraph("CARGO POR REACTIVACIÓN DE ATM: "+"IB-3E3").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
            dataTable.addCell(new Cell().add(new Paragraph(234+" EUR").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
            dataTable.addCell(new Cell().add(new Paragraph("0.00 EUR").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
            dataTable.addCell(new Cell().add(new Paragraph(11+" EUR").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        }
        else{
            dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
            dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
            dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
            dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
            dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        }

        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));

        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));

        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        dataTable.addCell(new Cell().add(new Paragraph("").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));

        document.add(dataTable);

        /** summary payment info **/
        var summaryPaymentTable = new Table(new float[] {120,120});
        summaryPaymentTable.setHorizontalAlignment(HorizontalAlignment.RIGHT);


        summaryPaymentTable.addCell(new Cell().add(new Paragraph("SUBTOTAL:").setFontSize(dataFont).setFontColor(backgroundBlueColor)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        summaryPaymentTable.addCell(new Cell().add(new Paragraph(1+" EUR").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));

        summaryPaymentTable.addCell(new Cell().add(new Paragraph("IMPUESTOS (IVA "+1+" % ):").setFontSize(dataFont).setFontColor(backgroundBlueColor)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        summaryPaymentTable.addCell(new Cell().add(new Paragraph(2+" EUR").setFontSize(dataFont).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));

        summaryPaymentTable.addCell(new Cell().add(new Paragraph("TOTAL PAGAR:").setFontSize(dataFont).setBold().setFontColor(backgroundBlueColor)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));
        summaryPaymentTable.addCell(new Cell().add(new Paragraph(3+" EUR").setFontSize(dataFont).setBold().setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));

        summaryPaymentTable.addCell(new Cell(0, 4).add(new Paragraph("Vencimiento a la vista").setFontSize(dataFont)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setMinHeight(minHeight));

        document.add(summaryPaymentTable);

        /** payment method **/
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        var paymentInfoTable = new Table(new float[] {100,180});
        paymentInfoTable.setMaxWidth(280);

        paymentInfoTable.addCell(new Cell(0, 4).add(new Paragraph("INFORMACIÓN DE PAGO").setFontSize(10f).setBackgroundColor(backgroundBlueColor).setFontColor(ColorConstants.WHITE)).setBorder(Border.NO_BORDER));

        if("perfect".equals("perfect")){

            paymentInfoTable.addCell(new Cell().add(new Paragraph("MÉTODO DE PAGO:").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
            paymentInfoTable.addCell(new Cell().add(new Paragraph("TARJETA BANCARIA").setFontSize(infoFont)).setBorder(Border.NO_BORDER));

            paymentInfoTable.addCell(new Cell().add(new Paragraph("TARJETA:").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
            paymentInfoTable.addCell(new Cell().add(new Paragraph("5541").setFontSize(infoFont)).setBorder(Border.NO_BORDER));

            paymentInfoTable.addCell(new Cell().add(new Paragraph("VENCIMIENTO:").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
            paymentInfoTable.addCell(new Cell().add(new Paragraph("02/28").setFontSize(infoFont)).setBorder(Border.NO_BORDER));

            paymentInfoTable.addCell(new Cell().add(new Paragraph("TIPO:").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
            paymentInfoTable.addCell(new Cell().add(new Paragraph("VISA:5541").setFontSize(infoFont)).setBorder(Border.NO_BORDER));

            paymentInfoTable.addCell(new Cell().add(new Paragraph("CÓDIGO AUT:").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
            paymentInfoTable.addCell(new Cell().add(new Paragraph("66-55412").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
        }else{
            paymentInfoTable.addCell(new Cell().add(new Paragraph("MÉTODO DE PAGO:").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
            paymentInfoTable.addCell(new Cell().add(new Paragraph("TRANSFERENCIA BANCARIA").setFontSize(infoFont)).setBorder(Border.NO_BORDER));

            paymentInfoTable.addCell(new Cell().add(new Paragraph("CUENTA DESTINO:").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
            paymentInfoTable.addCell(new Cell().add(new Paragraph("ES3020859736220330290849").setFontSize(infoFont)).setBorder(Border.NO_BORDER));
        }



        paymentInfoTable.addCell(new Cell(0, 4).add(new Paragraph("Nota: Factura generada automáticamente, al realizar el pago del producto o servicio solicitado").setFontSize(infoFont)).setBorder(Border.NO_BORDER));

        document.add(paymentInfoTable);

        document.close();

        return out;
    }

}
