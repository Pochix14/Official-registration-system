import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.text.*;

/**
 * @author Pablo Rodriguez 
 * @version 20.11.2020
 */
public class Usuario extends JFrame
{
    //Etiquetas
    private JLabel name, ced, dep, acon, he;
    
    //Area de indicaciones
    private JTextArea inicio;
    
    //Panel para datos
    public JTextField nombre, cedula, horaEntrada;
    
    //Panel de opciones del dep y la ac
    public JComboBox departamento, ac;
    
    //Panel
    private JPanel primario;
    
    //Boton de guardar
    public JButton guardar, cancelar;
    
    //Para diferenciar si es agregar o actualizar
    public int opcion;

    /**
     * Constructor 
     */
    public Usuario(Controlador control)
    {
        //Configuracion de panel tipo form
        primario = new JPanel();
        primario.setAlignmentX(Component.LEFT_ALIGNMENT);        
        
        //Etiquetas
        name = new JLabel("Funcionario:");
        ced = new JLabel("Cedula:");
        dep = new JLabel("Departamento:");
        acon = new JLabel("Area Conservacion:");
        he = new JLabel("Hora Entrada:");
        
        //Area de texto con indicaciones
        String indicaciones = "Ingrese la informacion del usuario.\nLa cedula debe ir en formato de 9 digitos sin espacios ni guiones! \n\n";
        inicio = new JTextArea(indicaciones);
        inicio.setBounds(3, 3, 400, 50);
        inicio.setWrapStyleWord(true); //Wrap!
        //inicio.setEditable(false);
        //inicio.setFocusable(false);
        //inicio.setOpaque(false);
        //inicio.setBackground(UIManager.getColor("Label.background"));
        inicio.setFont(UIManager.getFont("Label.font"));
        inicio.setBorder(UIManager.getBorder("Label.border"));
        this.add(inicio);
        
        //JTextField
        nombre = new JTextField(30);
        cedula = new JTextField(20);
        horaEntrada = new JTextField(20);
        
        //ComboBox
        departamento = new JComboBox();
        departamento.addItem("Asesoría Jurídica");
        departamento.addItem("Auditoria Interna");
        departamento.addItem("Control Interno");
        departamento.addItem("Cooperación Técnica y Financiera");
        departamento.addItem("CUSBSE");
        departamento.addItem("Dirección Administrativa Financiera");
        departamento.addItem("Dirección Ejecutiva");
        departamento.addItem("Dirección Técnica");
        departamento.addItem("Financiero Contable");
        departamento.addItem("Información y Regularización Territorial");
        departamento.addItem("Infraestructura");
        departamento.addItem("Participación Ciudadana y Gobernanza");
        departamento.addItem("Planificación y Evaluación");
        departamento.addItem("Prensa y Comunicación");
        departamento.addItem("Prevención, Protección y Control");
        departamento.addItem("Proveeduría Institucional");
        departamento.addItem("Recursos Humanos");
        departamento.addItem("Servicios Generales");
        departamento.addItem("Tecnologías de Información");
        ac = new JComboBox();
        ac.addItem("Sec Eje");
        ac.addItem("ACC");
        ac.addItem("ACOPAC");
        ac.addItem("ACMC");
        ac.addItem("ACAT");
        ac.addItem("ACAHN");
        ac.addItem("ACLAP");
        ac.addItem("ACLAC");
        ac.addItem("ACT");
        ac.addItem("ACG");
        ac.addItem("ACTo");
        this.crearPanel();
        this.add(primario);
        
        //Boton
        guardar = new JButton("Guardar");
        guardar.addActionListener(control);
        this.add(guardar);
        
        //Configuracion ventana
        this.setLayout(new FlowLayout());
        this.setTitle("Funcionario");
        this.setSize(500, 500);
        this.setMinimumSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }
    
    /**
     * Hacer visible o no
     */
    public void visible (boolean tf)
    {        
        this.setVisible(tf);
    }
    
    /**
     * Creacion de panel tipo form
     */
    private void crearPanel()
    {
        GroupLayout layout = new GroupLayout(primario);
        primario.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING) //Columna de etiquetas
                .addComponent(name)
                .addComponent(ced)
                .addComponent(dep)
                .addComponent(acon)
                .addComponent(he))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING) //Columna de campos
                .addComponent(nombre)
                .addComponent(cedula)
                .addComponent(departamento)
                .addComponent(ac)
                .addComponent(horaEntrada))); 
                
        layout.setVerticalGroup(layout.createSequentialGroup() //Filas de campos
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(name)
                .addComponent(nombre))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(ced)
                .addComponent(cedula))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(dep)
                .addComponent(departamento))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(acon)
                .addComponent(ac))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(he)
                .addComponent(horaEntrada)));
    }
    
    /**
     * Devuelve la opcion respectiva a si es agregar o actualizar usuarios
     */
    public int estado ()
    {
        return opcion;
    }
    
    /**
     * Modifica la seleccion de los combo box
     */
    public void modificarCB (int [] opciones)
    {
        departamento.setSelectedIndex(opciones[0]);
        ac.setSelectedIndex(opciones[1]);
    }
    
    /**
     * Limpia campos
     */
    public void limpiarCampos ()
    {
        nombre.setText("");
        cedula.setText("");
        horaEntrada.setText("");
        departamento.setSelectedIndex(0);
        ac.setSelectedIndex(0);
    }
}




































