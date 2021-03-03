import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.*;

import javax.swing.*;
import java.io.File;
import java.awt.*;

/**
 * @author Pablo Rodriguez
 * @version 1.12.2020
 */
public class PDF
{
    /**
     * Constructor
     */
    public PDF()
    {        
    }
    
    /**
     * Crear archivo de reporte
     */
    public static void crearReporte (JTable tabla)
    {
        try
        {
            File docs = new File(System.getProperty("user.home", "Documents")); //Obtiene ruta del usuario
            String ruta = docs.getPath() + "\\Documents\\Reporte Marcas - SINAC.pdf"; //ruta final
            Document documento = new Document(PageSize.A4.rotate()); //Crea doc y lo orienta horizontal
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));
            documento.open();
            ponerTitulo (documento);
            crearTabla(documento, tabla);
            documento.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Metodo que crea tabla con los datos de la base de datos
     */
    public static void crearTabla (Document documento, JTable tablaDatos) throws DocumentException
    {
        PdfPTable tabla = new PdfPTable(11); //Crear tabla cantidad de columnas 
        tabla.setWidths(new float [] {35, 250, 85, 120, 130, 90, 100, 100, 95, 85, 75}); //Tamano de cada columna
        tabla.setWidthPercentage(100);

        //Celda de numero
        PdfPCell celda = new PdfPCell(new Phrase("N°", FontFactory.getFont("Arial", 11, Font.BOLD)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Columna Nombre
        
        celda = new PdfPCell(new Phrase("Nombre", FontFactory.getFont("Arial", 11, Font.BOLD)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Celda Cedula
        celda = new PdfPCell(new Phrase("Cédula", FontFactory.getFont("Arial", 11, Font.BOLD)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Celda AC
        celda = new PdfPCell(new Phrase("Área Conservación", FontFactory.getFont("Arial", 11, Font.BOLD)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Celda Departamento
        celda = new PdfPCell(new Phrase("Departamento", FontFactory.getFont("Arial", 11, Font.BOLD)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Dia
        celda = new PdfPCell(new Phrase("Fecha", FontFactory.getFont("Arial", 11, Font.BOLD)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Hora Entrada
        celda = new PdfPCell(new Phrase("Entrada", FontFactory.getFont("Arial", 11, Font.BOLD)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Hora Salida
        celda = new PdfPCell(new Phrase("Salida", FontFactory.getFont("Arial", 11, Font.BOLD)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Tiempo Laborado
        celda = new PdfPCell(new Phrase("Tiempo Laborado", FontFactory.getFont("Arial", 11, Font.BOLD)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Tiempo Extra
        celda = new PdfPCell(new Phrase("Tiempo Extra", FontFactory.getFont("Arial", 11, Font.BOLD)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Llegada tardia
        celda = new PdfPCell(new Phrase("Tardía", FontFactory.getFont("Arial", 11, Font.BOLD)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        tabla.setHeaderRows(1);
        
        //Ciclo que llena los campos con los datos de la DB
        for (int i = 0; i < tablaDatos.getRowCount(); ++i)
        {
            tabla.addCell(new Phrase(Integer.toString (i + 1), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 1).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 2).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 3).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 4).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 5).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 6).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 7).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 8).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));                                
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 9).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 10).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
        }
        
        documento.add(tabla);
    }
    
    /**
     * Metodo para personalizar titulo del documento
     */
    public static void ponerTitulo (Document documento) throws DocumentException
    {
        Font fontbold = FontFactory.getFont("Times-Roman", 22, Font.BOLD);
        Paragraph p = new Paragraph("Reporte marcas - SINAC", fontbold);
        p.setSpacingAfter(20);
        p.setAlignment(1); //centro
        documento.add(p);
    }
    
    /**
     * Devuelve ruta del archivo
     */
    public String ruta ()
    {
        return System.getProperty("user.home", "Documents")+ "\\Documents\\Reporte Marcas - SINAC.pdf";
    }
}
