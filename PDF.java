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
        PdfPTable tabla = new PdfPTable(9); //Crear tabla cantidad de filas 
        tabla.setWidths(new float [] {1f, 9f, 4f, 5.5f, 6f, 3.8f, 4.2f, 4.2f, 3.5f}); //Tamano de cada fila

        //Celda de numero
        PdfPCell celda = new PdfPCell(new Phrase("N°"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Columna Nombre
        celda = new PdfPCell(new Phrase("Nombre"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Celda Cedula
        celda = new PdfPCell(new Phrase("Cedula"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Celda AC
        celda = new PdfPCell(new Phrase("Area Conservacion"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Celda Departamento
        celda = new PdfPCell(new Phrase("Departamento"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Dia
        celda = new PdfPCell(new Phrase("Fecha"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Hora Entrada
        celda = new PdfPCell(new Phrase("Entrada"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Hora Salida
        celda = new PdfPCell(new Phrase("Salida"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        //Tiempo Extra
        celda = new PdfPCell(new Phrase("Tiempo Extra"));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        
        tabla.setHeaderRows(1);
        
        //Ciclo que llena los campos con los datos de la DB
        for (int i = 0; i < tablaDatos.getRowCount(); ++i)
        {
            tabla.addCell(new Phrase(Integer.toString (i + 1), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 0).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 1).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 2).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 3).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 4).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 5).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 6).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            tabla.addCell(new Phrase(tablaDatos.getValueAt(i, 7).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));                                
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
