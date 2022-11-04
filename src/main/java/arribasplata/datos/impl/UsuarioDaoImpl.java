
package arribasplata.datos.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import arribasplata.database.Conexion;
import arribasplata.datos.UsuarioDao;
import arribasplata.dominio.Usuario;

import java.sql.SQLException;

public class UsuarioDaoImpl implements UsuarioDao<Usuario> {
    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public UsuarioDaoImpl(){
        CON=Conexion.getInstancia();
    }
    
    @Override
    public List<Usuario> listar(String texto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean insertar(Usuario obj) {
    	boolean resp=false;
        try {
            ps = CON.conectar().prepareStatement("INSERT INTO usuario (email,nombre,password,activo) VALUES (?,?,?,1)");
            ps.setString(1, obj.getEmail());
            ps.setString(2, obj.getNombre());
            ps.setString(3, obj.getPassword());
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public boolean actualizar(Usuario obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean desactivar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean activar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario login(String email, String clave) {
        Usuario usu = null;
        try {
            ps = CON.conectar().prepareStatement("select u.id, u.email,r.nombre,u.activo from usuarios u inner join roles r on u.rol_id=r.id where u.email=? and u.password=?");
            ps.setString(1, email);
            ps.setString(2, clave);
            rs = ps.executeQuery();
            if (rs.first()) {
                usu = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getBoolean(4));
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            rs = null;
            ps = null;
            CON.desconectar();
        }
        return usu;
    }
    /*
    public static void main(String[] args) {
    	UsuarioDaoImpl datos=new UsuarioDaoImpl();
    	Usuario usu=datos.login("admin@prueba.com", "admin")
    	System.out.println()
    }*/
    
}
