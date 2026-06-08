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
import java.util.List;

public class PDFExporter{

    public static boolean crearReporteCalificaciones(String materia, String grupo, String semestre, String[] encabezados, List<String[]> datos) {
        try {
            String userHome = System.getProperty("user.home");
            String nombreArchivo = "Calificaciones_" + materia.replaceAll(" ", "_") + "_" + grupo + ".pdf";
            String dest = userHome + File.separator + "Documents" + File.separator + nombreArchivo;

            File file = new File(dest);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);

            float[] columnWidths = {2, 5, 2, 2, 2, 2};
            Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

            PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont fontNormal = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            Cell mainHeader = new Cell(1, 6)
                    .add(new Paragraph("REPORTE OFICIAL DE CALIFICACIONES"))
                    .setFont(fontBold)
                    .setFontSize(14)
                    .setFontColor(DeviceGray.WHITE)
                    .setBackgroundColor(new DeviceRgb(45, 111, 164))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(8);
            table.addHeaderCell(mainHeader);

            Cell subHeader = new Cell(1, 6)
                    .add(new Paragraph("Materia: " + materia + "   |   Semestre: " + semestre + "   |   Grupo: " + grupo))
                    .setFont(fontBold)
                    .setFontSize(11)
                    .setBackgroundColor(new DeviceGray(0.95f))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(5);
            table.addHeaderCell(subHeader);

            for (String h : encabezados) {
                table.addHeaderCell(new Cell()
                        .add(new Paragraph(h))
                        .setFont(fontBold)
                        .setFontSize(10)
                        .setBackgroundColor(new DeviceGray(0.9f))
                        .setTextAlignment(TextAlignment.CENTER));
            }

            for (String[] fila : datos) {
                for (int i = 0; i < fila.length; i++) {
                    Cell celda = new Cell().add(new Paragraph(fila[i])).setFont(fontNormal).setFontSize(10);
                    
               
                    if (i != 1) {
                        celda.setTextAlignment(TextAlignment.CENTER);
                    }
                    table.addCell(celda);
                }
            }

            document.add(table);
            document.close();
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}