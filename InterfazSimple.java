import javax.swing.*;
import java.util.*;

/**
 * 
 * @author Pablo Rodriguez 
 * @version 20.11.2020
 */
public class InterfazSimple extends JOptionPane
{
    /**
     * Constructor 
     */
    public InterfazSimple()
    {
        
    }
    
    /**
     * Mensaje generado si hay un error en la marca
     */
    public void mensajeError ()
    {
        String mensaje = "Lo sentimos, intentelo de nuevo";
        this.showMessageDialog(null, mensaje, "Error marca", this.ERROR_MESSAGE);
    }
    
    /**
     * Mensaje si se conecto a la DB
     */    
    public void mensajeConexionDB ()
    {
        String mensaje = "Conexion Exitosa!";
        this.showMessageDialog(null, mensaje, "Conexion Base de Datos", this.INFORMATION_MESSAGE);
    }
    
    /**
     * Mensaje si NO se conecto a la DB
     */
    public void errorConexionDB ()
    {
        String mensaje = "Conexion Fallida!";
        this.showMessageDialog(null, mensaje, "Error Conexion", this.ERROR_MESSAGE);
    }
    
    /**
     * Opciones para hacer con la tabla del reporte
     */
    public int opcionesReporte ()
    {
        Object [] opciones = {"Enviar por correo", "Imprimir", "Cancelar"};
        int seleccion = this.showOptionDialog(null, "Qué desea hacer..", "Escoja una opción", this.YES_NO_CANCEL_OPTION, this.QUESTION_MESSAGE, null, opciones, "Cancelar");        
        //Devuelve 0 para correo, 1 imprimir y 2 cancelar
        return seleccion;
    }
    
    /**
     * Muestra las instrucciones para realizar el regsitro de marca
     */    
    public void instRegistro ()
    {
        String mensaje = "Para realizar el registro o marca de entrada y salida, únicamente debe pasar el código de barras de su carnét por el lector y esperar a que le muestre un mensaje de bienvenida.\n\n";
        String mensaje1 = "En caso contrario se le mostrará un mensaje de error y le pedirá que lo intente nuevamente.\n\nRealice el proceso hasta que el registro se complete correctamente.";
        this.showMessageDialog(null, mensaje + mensaje1, "Instrucciones Registro Entrada - Salida", this.INFORMATION_MESSAGE);
    }
    
    /**
     * Muestra instrucciones para generar un reporte
     */
    public void instReporte ()
    {
        String i = "1- Seleccione una o varias casillas para personalizar el reporte o bien, seleccione reporte completo.\n";
        String ii = "2- Llene los datos correspondientes a la(s) casilla(s) que selecciono.\n";
        String iii = "3- Una vez lleno los datos, presione el boton de 'Crear Reporte'.\n\n ";
        this.showMessageDialog(null, i+ii+iii, "Instruciones Reporte", this.INFORMATION_MESSAGE);
    }
    
    /**
     * Muestra los creditos
     */    
    public void creditos()
    {
        String mensaje = "Autor: Pablo Rodríguez Navarro\nVersión: 1.5.0\n©® Derechos Reservados ©®";
        this.showMessageDialog(null, mensaje, "Desarrollado por", this.INFORMATION_MESSAGE);
    }
    
    /**
     * Solicita correo para enviar PDF con reporte
     */
    public String pedirCorreo ()
    {
        String mensaje = "Digite su correo electronico: ";
        
        String a = this.showInputDialog(mensaje);
        if (a == null || a == "")
        {
            a = "";
        }
        
        return a;
    }
    
    /**
     * Solicita cedula para editar usuario
     */
    public String pedirCedula ()
    {
        String mensaje = "Digite la cedula del funcionario a editar: ";
        String a = this.showInputDialog(mensaje);
        if (a == null || a == "")
        {
            a = "";
        }
        
        return a;
    }    
    
    /**
     * Mensaje generado cuando se hizo el envio del correo
     */
    public void correoEnviado ()
    {
        String mensaje = "Correo enviado correctamente!";
        JOptionPane p = new JOptionPane(mensaje, this.INFORMATION_MESSAGE);
        JDialog dialogo = p.createDialog("Correo Enviado");
        
        //Timer para autocierre
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask()
            {
                public void run ()
                {
                    dialogo.dispose();
                }
            }, 3000); //Cierre en 3 seg
            
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setAlwaysOnTop(true);
        dialogo.setModal(false);
        dialogo.setVisible(true);
    }
    
    /**
     * Mensaje de error cuando valida que la entrada de cedula no son numeros
     */
    public void errorNumeros ()
    {
        String mensaje = "Ingrese unicamente numeros";
        this.showMessageDialog(null, mensaje, "ERROR!", this.ERROR_MESSAGE);
    }
    
    /**
     * Mensaje al generar una marca de entrada o salida
     */
    public void marcaRegistrada (String entradaSalida, String nombre)
    {
        String mensaje = "";
        if (entradaSalida == "entrada")
        {
            mensaje = "Marca de entrada registrada exitosamente!\n\nBuen dia! \n" + nombre;
        }
        else
        {
            mensaje = "Marca de salida registrada exitosamente!\n\nBuena tarde! \n"+ nombre;
        }
        JOptionPane p = new JOptionPane(mensaje, this.INFORMATION_MESSAGE);
        JDialog dialogo = p.createDialog("Marca registrada!");
        
        //Timer para que se autocierre
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask ()
            {
                public void run ()
                {
                    dialogo.dispose();
                }
            }, 2500); //Cierre automatico en 3 segundos
        
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setAlwaysOnTop(true);
        dialogo.setModal(false);
        dialogo.setVisible(true);
    }
    
    /**
     * Mensaje de agregar / actualizar / eliminar correctamente
     */
    public void usuario (int tipo)
    {
        String mensaje = "";
        switch (tipo)
        {
            case 1: //Agregar
                mensaje = "Usuario registrado correctamente!";
            break;
            
            case 2: //Actualizar
                mensaje = "Usuario actulizado correctamente!";
            break;
            
            case 3: //Eliminar
                mensaje = "Usuario eliminado correctamente!";
            break;
        }
        JOptionPane p = new JOptionPane(mensaje, this.INFORMATION_MESSAGE);
        JDialog dialogo = p.createDialog("Gestion de usuarios!");
        
        //Timer para que se autocierre
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask ()
            {
                public void run ()
                {
                    dialogo.dispose();
                }
            }, 2000); //Cierre automatico en 3 segundos
        
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setAlwaysOnTop(true);
        dialogo.setModal(false);
        dialogo.setVisible(true);
    }
    
    /**
     * Mensaje de imprimiendo
     */
    public void imprimiendo ()
    {
        String mensaje = "Imprimiendo documento..";
        JOptionPane pane = new JOptionPane(mensaje, this.INFORMATION_MESSAGE);
        JDialog dialogo = pane.createDialog("Imprimiendo PDF");
        
        //Timer de autocerrado
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask ()
        {
            public void run ()
            {
                dialogo.dispose();
            }
        }, 4000);
        
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setAlwaysOnTop(true);
        dialogo.setModal(false);
        dialogo.setVisible(true);
    }
    
    /**
     * Mensaje de error al estar algun campo incorrecto para informe
     */
    public void errorReporte (String mensaje)
    {
        this.showMessageDialog(null, mensaje, "ERROR!", this.ERROR_MESSAGE);
    }
}