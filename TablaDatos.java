import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;

/**
 * @author Pablo Rodriguez 
 * @version 11.12.2020
 */
public class TablaDatos
{
    private JMenuBar menu;
    private JMenu opciones;
    private JMenuItem guardar, imprimir;
    
    private JTable tabla;
    private JFrame ventana;
    private JScrollPane scroll;

    /**
     * Constructor 
     */
    public TablaDatos(DefaultTableModel modelo, Controlador c) //Solicita el modelo para la tabla y el listener de los botones
    {
        ventana = new JFrame("Resultado de Consulta");
        
        //Barra de menu
        menu = new JMenuBar();
        opciones = new JMenu("Opciones");
        guardar = new JMenuItem("Guardar");
        imprimir = new JMenuItem("Imprimir");
        opciones.add(guardar);
        opciones.add(imprimir);
        menu.add(opciones);
        this.activarListener(c); //Activa listener
        
        //Define la ventana con la tabla recibida
        tabla = new JTable();
        tabla.setModel(modelo);
        scroll = new JScrollPane(tabla);
        ventana.getContentPane().add(scroll);
        ventana.setMinimumSize(new Dimension(900,500));
        ventana.setJMenuBar(menu);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);        
    }
    
    /**
     * Activa listener de botones de opciones de la tabla 
     */
    public void activarListener (Controlador c)
    {
        guardar.addActionListener(c);
        imprimir.addActionListener(c);
    }
    
    /**
     * Devuelve tabla con datos de la consulta
     */
    public JTable tablaConsulta ()
    {
        return tabla;
    }
}
