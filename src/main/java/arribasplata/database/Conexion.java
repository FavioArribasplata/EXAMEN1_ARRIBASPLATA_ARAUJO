package arribasplata.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String DB = "dbexamen01";
    private final String USUARIO = "root";
    private final String PASSWORD = "";

    public Connection con;
    public static Conexion instancia;

    private Conexion() {
        this.con = null;
    }

    public Connection conectar() {
        try {
            Class.forName(DRIVER);
            this.con = (Connection) DriverManager.getConnection(URL + DB, USUARIO, PASSWORD);
            System.out.println("Conectado!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return this.con;

    }

    public void desconectar() {
        try{
            this.con.close();
            System.out.println("Desconectado");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public synchronized static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
            System.out.println("Se creo la instancia!");
        }
        return instancia;
    }
}
