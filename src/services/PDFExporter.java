package services;

import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.io.font.constants.StandardFonts;

import java.io.File;
import java.io.IOException;
import java.util.List;
import models.User;

public class PDFExporter {

    public static void export(List<User> users, String dest) throws IOException {
        File file = new File(dest);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);

        float[] columnWidths = {1, 4, 3, 3, 3, 3};
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

        PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Cell mainHeader = new Cell(1, 6)
                .add(new Paragraph("REPORTE GENERAL DE USUARIOS"))
                .setFont(fontBold)
                .setFontSize(14)
                .setFontColor(DeviceGray.WHITE)
                .setBackgroundColor(new DeviceRgb(45, 111, 164))
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(8);
        table.addHeaderCell(mainHeader);

        String[] headers = {"#", "Email", "Password", "País", "Lenguaje", "Género"};
        for (String h : headers) {
            table.addHeaderCell(new Cell()
                    .add(new Paragraph(h))
                    .setFont(fontBold)
                    .setBackgroundColor(new DeviceGray(0.9f))
                    .setTextAlignment(TextAlignment.CENTER));
        }

        int i = 1;
        for (User u : users) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(i++))).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(u.getEmail())));
            table.addCell(new Cell().add(new Paragraph(u.getPass())));
            table.addCell(new Cell().add(new Paragraph(u.getPais())));
            table.addCell(new Cell().add(new Paragraph(u.getLenguaje())));
            table.addCell(new Cell().add(new Paragraph(u.getGenero())));
        }

        document.add(table);
        document.close();
    }
}