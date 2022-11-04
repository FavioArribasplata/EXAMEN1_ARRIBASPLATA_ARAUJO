package arribasplata.datos.impl;

import java.util.List;

import arribasplata.database.Conexion;
import arribasplata.datos.CategoriaDao;
import arribasplata.dominio.Categoria;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.SQLException;

public class CategoriaDaoImpl implements CategoriaDao<Categoria> {
	private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public CategoriaDaoImpl(){
        CON=Conexion.getInstancia();
    }
    
    @Override
    public List<Categoria> listar(String texto) {
        List<Categoria> lista = new ArrayList();
        try {
            ps=CON.conectar().prepareStatement("SELECT * FROM categorias where nombre LIKE ?");
            ps.setString(1,"%"+texto+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                lista.add(new Categoria(rs.getInt(1),rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps=null;
            CON.desconectar();
        }
        return lista;
    }
    

    @Override
    public boolean insertar(Categoria obj) {
        boolean resp=false;
        try {
            ps = CON.conectar().prepareStatement("INSERT INTO categorias (nombre) VALUES (?)");
            ps.setString(1, obj.getNombre());
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
    public boolean editar(Categoria obj) {
        boolean resp=false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE categorias set nombre=? WHERE id=?");
            ps.setString(1, obj.getNombre());
            ps.setInt(2, obj.getId());
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
    public boolean eliminar(int id) {
        boolean resp=false;
        try {
            ps = CON.conectar().prepareStatement("DELETE FROM categorias WHERE id=?");
            ps.setInt(1, id);
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

    public static void main(String[] args) {
    	CategoriaDaoImpl datos=new CategoriaDaoImpl();
    	Categoria categoria =new Categoria();
    	categoria.setNombre("Cat JSP 2");
    	System.out.println(datos.insertar(categoria));
    	
    }

	@Override
	public Categoria obtener(int id) {
		Categoria reg=new Categoria();
        try {
            ps=CON.conectar().prepareStatement("SELECT * FROM categorias where id=?");
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                reg.setId(rs.getInt(1));
                reg.setNombre(rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps=null;
            CON.desconectar();
        }
        return reg;
	}
}
