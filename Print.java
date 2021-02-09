import java.io.*;
import javax.print.attribute.*;
import javax.print.*;
import javax.print.event.*;

import java.awt.print.*;


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
        //FileInputStream pdf = new FileInputStream(ruta);
        File pdf = new File(ruta);
        SimpleDoc simpleDoc = null;
        simpleDoc = new SimpleDoc(pdf.toURL(), DocFlavor.URL.AUTOSENSE, null);
        PrintService impresora = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob job = impresora.createPrintJob();
        //job.print(pdf, null);
    }
}
