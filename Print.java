import java.io.*;
import javax.print.attribute.*;
import javax.print.*;
import javax.print.event.*;

import java.awt.print.*;
import com.spire.pdf.*;


/**
 * @author Pablo Rodriguez
 * @version 10.12.2020
 */
public class Print
{   
    /**
     * Constructor 
     */
    public Print()
    {
        
    }

    public void imprimir (String ruta, InterfazSimple is) throws Exception
    {
        //Crea instancia del PDF y asigna ruta      
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(ruta);
        
        //Crea trabajo de impresion
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat formatoPagina  = printerJob.defaultPage();
        Paper papel = formatoPagina.getPaper();

        //Remueve los margenes por default y pone orientacion horizontal
        //papel.setSize(8.5, 11);
        papel.setImageableArea(formatoPagina.getImageableX(), formatoPagina.getImageableY(), formatoPagina.getWidth()+1, formatoPagina.getHeight());
        formatoPagina.setOrientation(PageFormat.LANDSCAPE);
        
        //Cargando papel al formato y asignando el documento a imprimir con el formato dado
        formatoPagina.setPaper(papel);
        printerJob.setPrintable(pdf, formatoPagina);

        //Para desplegar trabajo de impresion
        if (printerJob.printDialog())
        {
            try
            {
                printerJob.print();
                is.imprimiendo();
            } catch (PrinterException e)
            {
                e.printStackTrace();
            }
        }
    }
}












