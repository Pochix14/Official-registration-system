import java.awt.event.*;
import javax.swing.event.*;

import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.*;

/**
 * @author Pablo Rodriguez
 * @version 1.12.2020
 */
public class Controlador implements ActionListener, DocumentListener
{
    //Variables de otras clases
    private Interfaz interfaz;
    private InterfazSimple iSimple;
    private BaseDatos baseDatos;
    private Mail correo;
    private TablaDatos tabla;
    private PDF pdf;
    private Print print;
    
    private int contador,numeroCaracteres; //Contador para saber cuantos campos de busqueda se activaron y numeroCaracteres para contar caracteres de registro de marca

    /**
     * Constructor
     */
    public Controlador()
    {
        iSimple = new InterfazSimple();
        interfaz = new Interfaz();
        baseDatos = new BaseDatos();
        correo = new Mail();
        pdf = new PDF();
        print = new Print();
        
        contador = 0;
        numeroCaracteres = 0;
        
        interfaz.activarListener(this);
        interfaz.visible(true);
    }
    
    /**
     * Eventos del document listener
     */
    public void changedUpdate (DocumentEvent evento)
    {
        
    }
    @Override
    public void insertUpdate (DocumentEvent evento)
    {
        ++numeroCaracteres;               
        if (numeroCaracteres == 9 || interfaz.codigo.getText().length() == 9) //Se ponen 9 por ser la cantidad de digitos de la cedula
        {
            if (!interfaz.codigo.getText().matches("\\d{9}")) //Valida que solo sean numeros
            {
                iSimple.errorNumeros();
                this.asistente();
                numeroCaracteres = 0;
            }
            else
            {
                Calendar calendario = new GregorianCalendar();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
                String [] fecha = formato.format(calendario.getTime()).split(" "); //Corta en 3 partes la fecha, la 0 es la fecha, la 1 la hora y la 2 el am o pm
                if (fecha[2].contains("a"))
                {
                    fecha[2] = "AM";
                }
                if (fecha[2].contains("p"))
                {
                    fecha[2] = "PM";
                }
                String marca = "";
                if (baseDatos.consultarExiste(interfaz.codigo.getText(), fecha[0]) == false) //Pregunta si existe el registro, si NO existe hace lo siguiente
                {
                    marca = "entrada";
                    baseDatos.anadirMarca(marca, interfaz.codigo.getText(), fecha[0], fecha[1] + " " + fecha[2]); //Anade marca con datos adquiridos
                    iSimple.marcaRegistrada(marca, baseDatos.obtenerNombre(interfaz.codigo.getText())); //Lanza mensaje de marca
                    this.asistente(); //Limpia campos
                    numeroCaracteres = 0;
                }
                else //Si ya existe marca, procede a marcar la salida
                {
                    marca = "salida";
                    baseDatos.anadirMarca(marca, interfaz.codigo.getText(), fecha[0], fecha[1] + " " + fecha[2]); //Marca la salida
                    baseDatos.tiempoExtra(this.extras(baseDatos.obtenerHoras(interfaz.codigo.getText(), fecha[0])), interfaz.codigo.getText(), fecha[0]);  //Verifica si hay tiempo extra para marcar
                    iSimple.marcaRegistrada(marca, baseDatos.obtenerNombre(interfaz.codigo.getText())); //Mensaje de marca registrada
                    this.asistente(); //limpia campos
                    numeroCaracteres = 0;
                }               
            }            
        }
    }
    private void asistente () //Limpia campo de codigo
    {
        //Se ejecuta desde un run para que sea otro hilo
        Runnable run = new Runnable ()
        {
            @Override
            public void run()
            {
                interfaz.codigo.setText(""); //Limpia campo de la interfaz donde se ingresa la cedula
            }
        };
        javax.swing.SwingUtilities.invokeLater(run); //Esto hace que se corra inmediatamente despues
    }
    public void removeUpdate (DocumentEvent evento)
    {
        if (numeroCaracteres > 0)
        {
            --numeroCaracteres;
        }        
    }
    
    /**
     * Saca calculo de horas extra
     */
    private String extras (String[] horas)
    {
        String extras = "0";        
        try
        {
            SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss"); //Formato que se le da al string que recibe de la DB
            Date entrada = formato.parse(horas[0]);
            Date salida = formato.parse(horas[1]);
            //Diferencia entre las horas
            long dif = salida.getTime() - entrada.getTime();
            //Sacar los minutos de diferencia
            long min = (dif / (1000 * 60)) % 60;
            //Obtenemos las horas de diferencia
            long hrs = (dif / (1000 * 60 * 60)) % 24;
            
            //Valida si existe tiempo extra y lo marca
            if (min > 0 || hrs > 0)
            {
                if (hrs >= 8) //Si hay tiempo extra, lo asigna con horas y minutos
                {
                    hrs -= 8; //resta las 8 horas laborales 
                    extras = Long.toString(hrs) + " horas y " + Long.toString(min) + " minutos";
                }
                else //Si no existe, marca 0
                {
                    hrs = 0;
                    min = 0;                    
                }                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }        
        return extras;
    }
    
    /**
     * Eventos que desarrolla cada botón
     */
    public void actionPerformed (ActionEvent evento)
    {
        //Botones de imprimir y guardar
        if (evento.getActionCommand().equals("Guardar"))
        {
            pdf.crearReporte(tabla.tablaConsulta()); //Obtiene tabla con datos y envia para generar PDF
            boolean r = correo.enviarMail(iSimple.pedirCorreo()); //Envia correo con reporte en PDF
            if (r)
            {
                iSimple.correoEnviado(); //Mensaje de correo enviado
            }            
        }
        
        if (evento.getActionCommand().equals("Imprimir"))
        {
            pdf.crearReporte(tabla.tablaConsulta()); //Obtiene tabla con datos y envia para generar PDF
            try 
            {
                print.imprimir(pdf.ruta(), iSimple);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }            
        }
        
        //Botones de la barra de menu
        if (evento.getActionCommand().equals("Créditos"))
        {
            iSimple.creditos();
        }
        
        if (evento.getActionCommand().equals("Ayuda Registro"))
        {
            iSimple.instRegistro();
        }
        
        if (evento.getActionCommand().equals("Ayuda Reporte"))
        {
            iSimple.instReporte();
        }
        
        //Acciones de los checkbox        
        if (evento.getSource() == interfaz.nombre) //Habilita o deshabilita los textfield's correspondientes y aumenta o disminuye el contador
        {
            if (interfaz.funcionario.isEnabled())
            {
                interfaz.funcionario.setEnabled(false);
                interfaz.crearReporte.setEnabled(false);
                --contador;
            }
            else
            {
                interfaz.funcionario.setEnabled(true);
                interfaz.crearReporte.setEnabled(true);
                interfaz.funcionario.requestFocusInWindow();
                ++contador;
            }
        }
        
        if (evento.getSource() == interfaz.cedula) //Habilita o deshabilita los textfield's correspondientes y aumenta o disminuye el contador
        {
            if (interfaz.ced.isEnabled())
            {
                interfaz.ced.setEnabled(false);
                interfaz.crearReporte.setEnabled(false);
                --contador;
            }
            else
            {
                interfaz.ced.setEnabled(true);
                interfaz.crearReporte.setEnabled(true);
                interfaz.ced.requestFocusInWindow();
                ++contador;
            }
        }
        
        if (evento.getSource() == interfaz.departamento) //Habilita o deshabilita los textfield's correspondientes y aumenta o disminuye el contador
        {
            if (interfaz.dep.isEnabled())
            {
                interfaz.dep.setEnabled(false);
                interfaz.crearReporte.setEnabled(false);
                --contador;
            }
            else
            {
                interfaz.dep.setEnabled(true);
                interfaz.crearReporte.setEnabled(true);
                interfaz.dep.requestFocusInWindow();
                ++contador;
            }
        }
        
        if (evento.getSource() == interfaz.fecha) //Habilita o deshabilita los textfield's correspondientes y aumenta o disminuye el contador
        {
            if (interfaz.fech.isEnabled())
            {
                interfaz.fech.setEnabled(false);
                interfaz.crearReporte.setEnabled(false);
                --contador;
            }
            else
            {
                interfaz.fech.setEnabled(true);
                interfaz.crearReporte.setEnabled(true);
                interfaz.fech.requestFocusInWindow();
                ++contador;
            }
        }
        
        if (evento.getSource() == interfaz.rango) //Habilita o deshabilita los textfield's correspondientes y aumenta o disminuye el contador
        {
            if (interfaz.rang.isEnabled())
            {
                interfaz.rang.setEnabled(false);
                interfaz.crearReporte.setEnabled(false);
                --contador;
            }
            else
            {
                interfaz.rang.setEnabled(true);
                interfaz.crearReporte.setEnabled(true);
                interfaz.rang.requestFocusInWindow();
                ++contador;
            }
        }        
        
        if (evento.getSource() == interfaz.acon) //Habilita o deshabilita los textfield's correspondientes y aumenta o disminuye el contador
        {
            if (interfaz.ac.isEnabled())
            {
                interfaz.ac.setEnabled(false);
                interfaz.crearReporte.setEnabled(false);
                --contador;
            }
            else
            {
                interfaz.ac.setEnabled(true);
                interfaz.crearReporte.setEnabled(true);
                interfaz.ac.requestFocusInWindow();
                ++contador;
            }
        }
        
        if (evento.getSource() == interfaz.completo) //Habilita el boton de reporte completo y deshabilita las demas opciones
        {
            if (interfaz.reporteCompleto.isEnabled())
            {
                interfaz.reporteCompleto.setEnabled(false);
            }
            else
            {
                interfaz.reporteCompleto.setEnabled(true);
                interfaz.reporteCompleto.requestFocusInWindow();
                if (interfaz.nombre.isSelected())
                {
                    interfaz.funcionario.setText("");
                    interfaz.funcionario.setEnabled(false);
                    interfaz.nombre.setSelected(false);
                }
                if (interfaz.cedula.isSelected())
                {
                    interfaz.ced.setText("");
                    interfaz.ced.setEnabled(false);
                    interfaz.cedula.setSelected(false);
                }
                if (interfaz.departamento.isSelected())
                {
                    interfaz.dep.setEnabled(false);
                    interfaz.departamento.setSelected(false);
                }
                if (interfaz.acon.isSelected())
                {
                    interfaz.ac.setEnabled(false);
                    interfaz.acon.setSelected(false);
                }
                if (interfaz.fecha.isSelected())
                {
                    interfaz.fech.setText("");
                    interfaz.fech.setEnabled(false);
                    interfaz.fecha.setSelected(false);
                }
                if (interfaz.rango.isSelected())
                {
                    interfaz.rang.setText("");
                    interfaz.rang.setEnabled(false);
                    interfaz.rango.setSelected(false);
                }
            }
        }
        
        //Acciones de los botones de reportes
        if (evento.getSource() == interfaz.crearReporte) //Crea el reporte deseado, validando los campos que tengan informacion y creando la consulta
        {
            String consulta = "select nombre, cedula, departamento, dia, entrada, salida, tiempoExtra from funcionario inner join horas on funcionario.cedula = horas.funcionario where ";
            String finalConsulta = " order by dia;";
            int ands = contador - 1;
            String and = " AND ";
            //Validar cada campo de datos
            if (interfaz.funcionario.isEnabled())
            {
                if (interfaz.funcionario.getText().length() > 0)
                {
                    String name = "funcionario.nombre = '" + interfaz.funcionario.getText() + "'";
                    if (ands == contador-1 || ands == 0)
                    {
                        consulta = consulta + name;
                    }
                    else
                    {
                        if (ands > 0)
                        {
                            consulta = consulta + and + name;
                            --ands;
                        }                        
                    }
                }
            }
            
            if (interfaz.ced.isEnabled())
            {
                if (interfaz.ced.getText().length() > 0)
                {
                    String cedula = "funcionario.cedula = '" + interfaz.ced.getText() + "'";
                    if (ands == contador-1 || ands == 0)
                    {
                        consulta = consulta + cedula;
                    }
                    else
                    {
                        if (ands > 0)
                        {
                            consulta = consulta + and + cedula;
                            --ands;
                        }                        
                    }
                }
            }
            
            if (interfaz.dep.isEnabled())
            {
                String dep = "funcionario.departamento = '" + interfaz.dep.getSelectedItem().toString() + "'";
                if (ands == contador - 1 || ands == 0)
                {
                    consulta = consulta + dep;
                }
                else
                {
                    if (ands > 0)
                    {                        
                        consulta = consulta + and + dep;
                        --ands;
                    }
                }
            }
            
            if (interfaz.ac.isEnabled())
            {
                String ac = "funcionario.ac = '" + interfaz.ac.getSelectedItem().toString() + "'";
                if (ands == contador - 1 || ands == 0)
                {
                    consulta = consulta + ac;
                }
                else
                {
                    if (ands > 0)
                    {
                         consulta = consulta + and + ac;
                         --ands;
                    }                   
                }
            }
            
            if (interfaz.fech.isEnabled())
            {
                //Preparar calendario
            }
            
            if (interfaz.rang.isEnabled())
            {
                //Preparar calendarios de fechas
            }
            
            //Construccion de consulta final
            consulta = consulta + finalConsulta;
            this.enviarConsulta(consulta);
        }
        
        if (evento.getSource() == interfaz.reporteCompleto) //Genera reporte completo 
        {
            String consulta = "select nombre, cedula, ac, departamento, dia, entrada, salida, tiempoExtra from funcionario inner join horas on funcionario.cedula = horas.funcionario order by dia;";
            this.enviarConsulta(consulta);
            interfaz.completo.setSelected(false);
            interfaz.reporteCompleto.setEnabled(false);
        }
    }
    
    /**
     * Crear parametros para consulta al SQL
     */
    public void enviarConsulta (String consulta)
    {
        tabla = new TablaDatos(baseDatos.consultar(consulta), this); //Envia consulta a la DB y crea tabla que muesta en pantalla
    }
}