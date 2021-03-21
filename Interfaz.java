import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.text.*;

import java.text.SimpleDateFormat;

/**
 * @author Pablo Rodríguez 
 * @version 20.11.2020
 */
public class Interfaz extends JFrame
{
    //Variables de menu
    private JMenuBar menu;
    private JMenu ayuda, usuarios;
    private JMenuItem insReg, insRep, creditos, agUser, edUser, elUser ;
    
    //Paneles
    private JPanel registro, reporte, rep1, rep2, rep3, rep4;
    
    //Etiquetas
    private JLabel etqReg, etqRep, logo;
    private JLabel etqFun, etqCed, etqDep, etqFecha, etqRangoI, etqRangoF, etqAC;
    
    //Tabulador de pestanas
    private JTabbedPane pestanas;
    
    //Titulo de ventana
    private final String titulo = "Registro Entrada - Salida SINAC";
    
    //Strings
    private String msjReg, msjRep;
    
    //Campo para introducir codigo de barras y datos de busqueda
    public JTextField codigo, funcionario, ced, fech, rang;
    
    //Lista de opciones para departamentos y ac's
    public JComboBox dep, ac;
    
    //Imagen con logo de SINAC
    private Image fondo;
    
    //Checkbox para reportes
    public JCheckBox nombre, cedula, departamento, fecha, rango, acon, completo;
    
    //Botones
    public JButton crearReporte, reporteCompleto;
    
    //Selector de fechas
    public com.toedter.calendar.JDateChooser fechaReporte;
    public com.toedter.calendar.JDateChooser fechaRangoInicio;
    public com.toedter.calendar.JDateChooser fechaRangoFin;
    
    /**
     * Constructor 
     */
    public Interfaz()
    {
        //Configuracion de ventana principal y panel de registro
        registro = new JPanel();
        registro.setPreferredSize(new Dimension(500, 500));
        registro.setLayout(new FlowLayout());        
        msjReg = "Por favor, escanee el codigo de barras de su carnet: ";
        etqReg = new JLabel(msjReg.toUpperCase());
        registro.add(etqReg);
        codigo = new JTextField(20);
        registro.add(codigo);        
        logo = new JLabel();
        logo.setMaximumSize(new Dimension(150, 150));
        ImageIcon icono = new ImageIcon("logo.jpg");
        fondo = icono.getImage();
        fondo = fondo.getScaledInstance(600, 540, java.awt.Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(fondo));
        registro.add(logo);
        
        //Configuracion de panel para reportes
        reporte = new JPanel();
        reporte.setPreferredSize(new Dimension(500, 500));
        reporte.setLayout(new BoxLayout(reporte, BoxLayout.Y_AXIS));
        this.crearPanelReporte();
        this.add(reporte);        
        
        //Configuracion de barra de menu
        menu = new JMenuBar();
        usuarios = new JMenu("Usuarios");
        agUser = new JMenuItem("Agregar Usuario");
        edUser = new JMenuItem("Editar Usuario");
        elUser = new JMenuItem("Eliminar Usuario");
        usuarios.add(agUser);
        usuarios.add(edUser);
        usuarios.add(elUser);
        ayuda = new JMenu("Ayuda");
        insReg = new JMenuItem("Ayuda Registro");
        insRep = new JMenuItem("Ayuda Reporte");
        creditos = new JMenuItem("Créditos");
        ayuda.add(insReg);
        ayuda.add(insRep);
        ayuda.add(creditos);
        menu.add(usuarios);
        menu.add(ayuda);
        
        //Configuracion para ventana principal
        this.setLayout(new FlowLayout());        
        this.setTitle(titulo);
        this.setJMenuBar(menu);
        this.setSize(610, 650);
        this.setMinimumSize(new Dimension(615, 655));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);        
        this.setResizable(false);
        pestanas = new JTabbedPane();
        pestanas.setPreferredSize(new Dimension (600, 630));
        pestanas.add("Registro", registro);
        pestanas.add("Reporte", reporte);
        this.add(pestanas);        
        
        //Metodo para que el textfield quede con el foco
        codigo.requestFocusInWindow();
    }
    
    /**
     * Mostrar ventana
     */
    public void visible (boolean tf)
    {
        this.setVisible(tf);
        codigo.requestFocusInWindow();
    }
    
    /**
     * Crear panel para reportes
     */
    public void crearPanelReporte ()
    {
        //Panel de instrucciones
        rep4 = new JPanel();
        rep4.setAlignmentX(Component.LEFT_ALIGNMENT);
        rep4.setBorder(BorderFactory.createTitledBorder("INSTRUCCIONES REPORTE"));
        String i = "1- Seleccione una o varias casillas para personalizar el reporte o bien, seleccione reporte completo.\n";
        String ii = "2- Llene los datos correspondientes a la(s) casilla(s) que selecciono.\n";
        String iii = "3- Una vez lleno los datos, presione el boton de 'Crear Reporte'.\n\n ";
        JLabel i1 = new JLabel(i);
        JLabel i2 = new JLabel(ii);
        JLabel i3 = new JLabel(iii);
        rep4.add(i1);
        rep4.add(i2);
        rep4.add(i3);
        reporte.add(rep4);
        
        //Panel superior con los checkbox para elegir como depurar el reporte
        rep1 = new JPanel();
        rep1.setAlignmentX(Component.LEFT_ALIGNMENT);
        rep1.setLayout(new BoxLayout(rep1, BoxLayout.Y_AXIS));
        msjRep = "Seleccione las opciones para generar reporte: \n";
        etqRep = new JLabel(msjRep);
        rep1.add(etqRep);
        nombre = new JCheckBox("Nombre");
        cedula = new JCheckBox("Cedula");
        departamento = new JCheckBox("Departamento");
        fecha = new JCheckBox("Fecha");
        rango = new JCheckBox("Rango Fechas");
        acon = new JCheckBox("Area Conservacion");
        completo = new JCheckBox("Reporte Completo");
        rep1.add(nombre);
        rep1.add(cedula);
        rep1.add(departamento);
        rep1.add(acon);
        rep1.add(fecha);
        rep1.add(rango);
        rep1.add(completo);
        reporte.add(rep1);
        
        //Panel inferior para el ingreso de datos especificos
        //Subpaneles para campos de entrada
        etqFun = new JLabel("Funcionario: ");
        funcionario = new JTextField(25);
        funcionario.setEnabled(false);
        etqCed = new JLabel("Cedula: ");
        ced = new JTextField(20);
        ced.setEnabled(false);
        etqDep = new JLabel("Departamento: ");
        //Lista de departamentos
        dep = new JComboBox();
        dep.addItem("Asesoría Jurídica");
        dep.addItem("Auditoria Interna");
        dep.addItem("Control Interno");
        dep.addItem("Cooperación Técnica y Financiera");
        dep.addItem("CUSBSE");
        dep.addItem("Dirección Administrativa Financiera");
        dep.addItem("Dirección Ejecutiva");
        dep.addItem("Dirección Técnica");
        dep.addItem("Financiero Contable");
        dep.addItem("Información y Regularización Territorial");
        dep.addItem("Infraestructura");
        dep.addItem("Participación Ciudadana y Gobernanza");
        dep.addItem("Planificación y Evaluación");
        dep.addItem("Prensa y Comunicación");
        dep.addItem("Prevención, Protección y Control");
        dep.addItem("Proveeduría Institucional");
        dep.addItem("Recursos Humanos");
        dep.addItem("Servicios Generales");
        dep.addItem("Tecnologías de Información");
        dep.setEnabled(false);
        etqAC = new JLabel("Area Conservacion: ");
        //Lista de AC's
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
        ac.setEnabled(false);
        //Fechas
        etqFecha = new JLabel("Fecha: ");
        fechaReporte = new com.toedter.calendar.JDateChooser(); 
        fechaReporte.setEnabled(false);
        etqRangoI = new JLabel("Fecha Inicio: ");
        fechaRangoInicio = new com.toedter.calendar.JDateChooser(); 
        etqRangoF = new JLabel("Fecha Fin: ");
        fechaRangoFin = new com.toedter.calendar.JDateChooser();
        fechaRangoInicio.setEnabled(false);
        fechaRangoFin.setEnabled(false);
        rep2 = new JPanel();
        rep2.setAlignmentX(Component.LEFT_ALIGNMENT);
        rep2.setBorder(BorderFactory.createTitledBorder("Datos busqueda"));        
        //Layout para agrupar tipo form
        GroupLayout layoutInferior = new GroupLayout(rep2);
        rep2.setLayout(layoutInferior);
        layoutInferior.setAutoCreateGaps(true);
        layoutInferior.setAutoCreateContainerGaps(true);
        layoutInferior.setHorizontalGroup(layoutInferior.createSequentialGroup()
            .addGroup(layoutInferior.createParallelGroup(GroupLayout.Alignment.LEADING) //Columna de etiquetas
                .addComponent(etqFun)
                .addComponent(etqCed)
                .addComponent(etqDep)
                .addComponent(etqAC)
                .addComponent(etqFecha)
                .addComponent(etqRangoI)
                .addComponent(etqRangoF))
            .addGroup(layoutInferior.createParallelGroup(GroupLayout.Alignment.LEADING) //Columna de campos
                .addComponent(funcionario)
                .addComponent(ced)
                .addComponent(dep)
                .addComponent(ac)
                .addComponent(fechaReporte)
                .addComponent(fechaRangoInicio)
                .addComponent(fechaRangoFin)));      
        
        layoutInferior.setVerticalGroup(layoutInferior.createSequentialGroup() //Filas de campos
            .addGroup(layoutInferior.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(etqFun)
                .addComponent(funcionario))
            .addGroup(layoutInferior.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(etqCed)
                .addComponent(ced))
            .addGroup(layoutInferior.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(etqDep)
                .addComponent(dep))
            .addGroup(layoutInferior.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(etqAC)
                .addComponent(ac))
            .addGroup(layoutInferior.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(etqFecha)
                .addComponent(fechaReporte))
            .addGroup(layoutInferior.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(etqRangoI)
                .addComponent(fechaRangoInicio))
            .addGroup(layoutInferior.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(etqRangoF)
                .addComponent(fechaRangoFin))); 
        reporte.add(rep2);
        
        //Panel de botones
        rep3 = new JPanel();
        rep3.setAlignmentX(Component.LEFT_ALIGNMENT);
        crearReporte = new JButton("Crear Reporte");
        reporteCompleto = new JButton("Reporte Completo");
        crearReporte.setEnabled(false);
        reporteCompleto.setEnabled(false);
        rep3.add(crearReporte);
        rep3.add(reporteCompleto);
        crearReporte.setAlignmentX(Component.RIGHT_ALIGNMENT);
        reporteCompleto.setAlignmentX(Component.RIGHT_ALIGNMENT);         
        reporte.add(rep3);       
    }
    
    /**
     * Activar el listener para cada botón
     */
    public void activarListener (Controlador control)
    {
        //Botones de la barra de menú
        insReg.addActionListener(control);
        insRep.addActionListener(control);
        creditos.addActionListener(control);
        agUser.addActionListener(control);
        edUser.addActionListener(control);
        elUser.addActionListener(control);
        
        //Checkbox
        nombre.addActionListener(control);
        cedula.addActionListener(control);
        departamento.addActionListener(control);
        acon.addActionListener(control);
        fecha.addActionListener(control);
        rango.addActionListener(control);
        completo.addActionListener(control);
        
        //Botones reporte
        crearReporte.addActionListener(control);
        reporteCompleto.addActionListener(control);
        
        //Panel de registro
        Document doc = codigo.getDocument();
        doc.addDocumentListener(control);    //Doc listener para que verifique cada vez que se ingresar un caracter en el campo codigo
        codigo.requestFocusInWindow();
    }
    
    /**
     * Limpiar campos reporte
     */
    public void limpiarCampos ()
    {
        //Checkbox
        nombre.setSelected(false);
        cedula.setSelected(false);
        departamento.setSelected(false);
        acon.setSelected(false);
        fecha.setSelected(false);
        rango.setSelected(false);
        completo.setSelected(false);
        
        //TextField
        funcionario.setText("");
        funcionario.setEnabled(false);
        ced.setText("");
        ced.setEnabled(false);
        
        //Listas
        dep.setEnabled(false);
        ac.setEnabled(false);
        
        //Fechas
        fechaReporte.setEnabled(false);
        fechaRangoInicio.setEnabled(false);
        fechaRangoFin.setEnabled(false);
        ((JTextField)fechaReporte.getDateEditor().getUiComponent()).setText("");
        ((JTextField)fechaRangoInicio.getDateEditor().getUiComponent()).setText("");
        ((JTextField)fechaRangoFin.getDateEditor().getUiComponent()).setText("");
        fechaReporte.setDate(null);
        
        //Boton de reporte
        crearReporte.setEnabled(false);
    }
    
    /**
     * Verifica campos para crear reporte o generar error
     */
    public boolean verificarCampos (InterfazSimple is)
    {
        boolean resultadoFinal = false; 
        
        boolean resultado1 = false;
        boolean resultado2 = false;
        boolean resultado3 = false;
        boolean resultado4 = false;;
        
        //Verifica que nombre de funcionario contenga al menos 3 caracteres
        if (funcionario.isEnabled() && funcionario.getText().length() < 4)
        {
            is.errorReporte("Debe ingresar al menos 3 letras");
            funcionario.setText("");
            funcionario.requestFocusInWindow();
            resultado1 = false;
        }
        else
        {
            resultado1 = true;
        }
        
        //Verifica que la cedula sea solo numeros y de 9 digitos
        if (ced.isEnabled() && ced.getText().length() != 9)
        {
            if (!ced.getText().matches("\\d{9}"))
            {
                is.errorReporte("Debe ingresar solo numeros y en formato de 9 digitos");
                ced.setText("");
                ced.requestFocusInWindow();
                resultado2 = false;
            }
            else
            {
                if (ced.getText().length() != 9)
                {
                    is.errorReporte("La cedula debe tener 9 digitos");
                    ced.setText("");
                    ced.requestFocusInWindow();
                    resultado2 = false;
                }
            }            
        }
        else
        {
            resultado2 = true;
        }
        
        //Verifica que la fecha no sea en blanco
        if (fechaReporte.isEnabled() && fechaReporte.getDate() == null)
        {
            Date fecha = new Date();
            fechaReporte.setDate(fecha);
            resultado3 = true;
        }
        else
        {
            resultado3 = true;
        }
        
        //Verifica que rango de fechas no sean en blanco
        if (fechaRangoInicio.isEnabled() && fechaRangoInicio.getDate() == null || fechaRangoFin.isEnabled() && fechaRangoFin.getDate() == null)
        {
            is.errorReporte("Debe seleccionar un rango de fechas!");
            ((JTextField)fechaRangoInicio.getDateEditor().getUiComponent()).setText("");
            ((JTextField)fechaRangoFin.getDateEditor().getUiComponent()).setText("");
            fechaRangoInicio.requestFocusInWindow();
            resultado4 = false;
        }
        else
        {
            resultado4 = true;
        }
        
        if (resultado1 && resultado2 && resultado3 && resultado4)
        {
            resultadoFinal = true;
        }
        
        return resultadoFinal;
    }   
}