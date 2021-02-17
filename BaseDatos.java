import java.sql.*;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * @author Pablo Rodriguez Navarro 
 * @version 25.11.2020
 */
public class BaseDatos
{    
    private Connection conexion;
    
    private boolean estado;
    
    private DefaultTableModel modelo;

    /**
     * Constructor 
     */
    public BaseDatos()
    {     
        estado = false;
        
        modelo = new DefaultTableModel();
        
        this.conectarDB();
    }

    /**
     * Conexion de DB
     */
    public void conectarDB ()
    {
        try
        {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver()); //Trae libreria de SQL Server
            String URL = "jdbc:sqlserver://PABLO-KCT\\SQLEXPRESS:1433;databaseName=prueba;user=admin;password=Pabloc14$;"; //Ruta para conexion
            conexion = DriverManager.getConnection(URL); //Abre conexion
            estado = true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Cerrar Conexion
     */
    public void cerrarDB ()
    {
        try
        {
            conexion.close();
            estado = false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }        
    }
    
    /**
     * Consulta estado de conexion
     */
    public boolean estadoDB ()
    {
        return estado;
    }
    
    /**
     * Query para consultas
     */
    public DefaultTableModel consultar (String consulta)
    {
        try
        {
            Statement stmt = conexion.createStatement();
            ResultSet resultado = stmt.executeQuery(consulta); //ResultSet es quien tiene los resultados del query
            this.configurarColumnas(resultado);
            this.vaciarFilas();
            this.meterDatos(resultado);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return modelo;
    }
    
    /**
     * Metodo que agarra los datos de la consulta y crea las columnas
     */
    private void configurarColumnas (ResultSet resultado)
    {
        try 
        {
             //Obtiene metadata para crear columnas
             ResultSetMetaData metaDatos = resultado.getMetaData();
             int columnas = metaDatos.getColumnCount(); //Numero de columnas
             Object [] etiquetas = new Object[columnas]; //Etiquetas de cada columna
             for (int i = 0; i < columnas; ++i)
             {
                 etiquetas[i] = metaDatos.getColumnLabel(i + 1).toUpperCase(); //Inicia en 1
             }
             modelo.setColumnIdentifiers(etiquetas); //Pone las etiquetas para las columnas de la tabla
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Metodo que vacia las filas de la tabla creada
     */
    private void vaciarFilas ()
    {
        try 
        {
            while (modelo.getRowCount() > 0)
            {
                modelo.removeRow(0);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Obtiene hora oficial de entrada del funcionario
     */
    public String horaOficial (String funcionario)
    {
        String ho = "";
        try
        {
            String consulta = "select entradaOficial from funcionario where cedula = '" + funcionario + "';";
            Statement stmt = conexion.createStatement();
            ResultSet r = stmt.executeQuery(consulta);
            if (r.next())
            {
                ho = r.getString(1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ho;
    }
    
    /**
     * Metodo que anade las columnas con los datos de la consulta
     */
    private void meterDatos (ResultSet resultado)
    {
        try
        {
            //Para cada registro del resultado de la consulta
            while (resultado.next())
            {
                Object [] datosFila = new Object[modelo.getColumnCount()]; //Objeto que contiene los datos de las filas
                for (int i = 0; i < modelo.getColumnCount(); ++i)
                {
                    datosFila[i] = resultado.getObject(i + 1); //Llena los datos con la informacion de la consulta
                }
                modelo.addRow(datosFila);
            }
            resultado.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
    * Consulta si existe un registro
    */
    public boolean consultarExiste (String funcionario, String dia)
    {
        boolean r = false;
        try
        {
            String uno = "if exists (select * from horas where funcionario = '" + funcionario + "' and dia = '" + dia + "')"; 
            String dos = " select 'true' else select 'false' return";
            String consulta = uno + dos; //Une los string's uno y dos para codigo de consulta en sql
            Statement stmt = conexion.createStatement();           
            ResultSet resultado = stmt.executeQuery(consulta);
            if (resultado.next()) //Si existen resultados
            {
                r = Boolean.parseBoolean(resultado.getString(1));  //Obtiene el resultado de la consulta y lo transforma en booleano           
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return r;
    }
    
    /**
     * Anade marcas
     */
    public void anadirMarca (String entradaSalida, String funcionario, String dia, String hora, String tardia)
    {
        if (entradaSalida == "entrada") //Valida si es de entrada o salida
        {
            try
            {
                String marcaEntrada = "insert into horas (funcionario, dia, entrada, salida, tiempoLaborado, tiempoExtra, tardia) values ('" + funcionario + "', '" + dia + "', '" + hora + "', '" + hora + "' , '0' , '0', '" + tardia + "');"; //Codigo para insertar nueva fila
                Statement stmt = conexion.createStatement();
                stmt.executeUpdate(marcaEntrada);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }            
        }
        else
        {
            try
            {
                String marcaSalida = "update horas set salida = '" + hora + "' where funcionario = '" + funcionario + "' and dia = '" + dia + "';"; //Codigo para actualizar campo de una fila
                Statement stmt = conexion.createStatement();
                stmt.executeUpdate(marcaSalida);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Obtiene las horas de entrada y salida
     */
    public String [] obtenerHoras (String funcionario, String dia)
    {
        String [] resultado = new String [2]; //Donde guarda hora de entrada y salida
        try
        {
            String consulta = "select entrada, salida from horas where funcionario = '" + funcionario + "' and dia = '" + dia + "';"; //Codigo de consulta
            Statement stmt = conexion.createStatement();                       
            ResultSet r = stmt.executeQuery(consulta);
            if (r.next())
            {
                resultado[0] = r.getString(1); //Guarda hora de entrada resultado de consulta
                if (resultado[0].contains("PM"))
                {
                    String [] div = resultado[0].split(":");
                    switch (div[0])
                    {
                        case "01": 
                                    div[0] = "13";
                        break;
                        
                        case "02": 
                                    div[0] = "14";
                        break;
                        
                        case "03": 
                                    div[0] = "15";
                        break;
                        
                        case "04": 
                                    div[0] = "16";
                        break;
                        
                        case "05": 
                                    div[0] = "17";
                        break;
                        
                        case "06": 
                                    div[0] = "18";
                        break;
                        
                        case "07": 
                                    div[0] = "19";
                        break;
                        
                        case "08": 
                                    div[0] = "20";
                        break;
                        
                        case "09": 
                                    div[0] = "21";
                        break;
                        
                        case "10": 
                                    div[0] = "22";
                        break;
                        
                        case "11": 
                                    div[0] = "23";
                        break;
                    }
                    resultado[0] = div[0] + ":" + div[1] + ":" + div[2];
                }
                else
                {
                    String [] div = resultado[0].split(":");
                    switch (div[0])
                    {
                        case "12":
                                    div[0] = "00";
                        break;
                    }
                    resultado[0] = div[0] + ":" + div[1] + ":" + div[2];
                }
                resultado[1] = r.getString(2); //Guarda hora de salida resultado de consulta
                //Cambia las horas a formato militar
                if (resultado[1].contains("PM"))
                {
                    String [] div = resultado[1].split(":");
                    switch (div[0])
                    {
                        case "01": 
                                    div[0] = "13";
                        break;
                        
                        case "02": 
                                    div[0] = "14";
                        break;
                        
                        case "03": 
                                    div[0] = "15";
                        break;
                        
                        case "04": 
                                    div[0] = "16";
                        break;
                        
                        case "05": 
                                    div[0] = "17";
                        break;
                        
                        case "06": 
                                    div[0] = "18";
                        break;
                        
                        case "07": 
                                    div[0] = "19";
                        break;
                        
                        case "08": 
                                    div[0] = "20";
                        break;
                        
                        case "09": 
                                    div[0] = "21";
                        break;
                        
                        case "10": 
                                    div[0] = "22";
                        break;
                        
                        case "11": 
                                    div[0] = "23";
                        break;
                    }
                    resultado[1] = div[0] + ":" + div[1] + ":" + div[2];
                }
                else
                {
                    String [] div = resultado[1].split(":");
                    switch (div[0])
                    {
                        case "12":
                                    div[0] = "00";
                        break;
                    }
                    resultado[1] = div[0] + ":" + div[1] + ":" + div[2];
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return resultado;
    }
    
    /**
     * Anade el tiempo extra y laborado
     */
    public void tiempo (String tiempos, String funcionario, String dia)
    {
        try
        {
            String [] resultado = tiempos.split("/"); //Separa el string recibido en tiempo laborado y extra
            
            //Sentencia para actualizar tiempo laborado
            String tl = "update horas set tiempoLaborado = '" + resultado[0] + "' where funcionario = '" + funcionario + "' and dia = '" + dia + "';";
            Statement stmt = conexion.createStatement();
            stmt.executeUpdate(tl);
            
            //Sentencia para actualizar tiempo extra
            String te = "update horas set tiempoExtra = '" + resultado[1] + "' where funcionario = '" + funcionario + "' and dia = '" + dia + "';";
            stmt.executeUpdate(te);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Obtiene nombre de funcionario
     */
    public String obtenerNombre (String cedula)
    {
        String nombre = "";
        try 
        {
            String consulta = "select nombre from funcionario where cedula = '" + cedula + "';"; //Codigo para obtener nombre del funcionario en base a la cedula
            Statement stmt = conexion.createStatement();
            ResultSet r = stmt.executeQuery(consulta);
            if (r.next())
            {
                nombre = r.getString(1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }       
        return nombre;
    }
}