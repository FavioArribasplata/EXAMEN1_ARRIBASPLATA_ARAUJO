package arribasplata.datos.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import arribasplata.database.Conexion;
import arribasplata.datos.ProductoDao;
import arribasplata.dominio.Categoria;
import arribasplata.dominio.Producto;

public class ProductoDaoImpl implements ProductoDao <Producto> {
    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public ProductoDaoImpl(){
        CON=Conexion.getInstancia();
    }
    
    @Override
    public List<Producto> listar(String texto,int totalPorPagina, int numPagina) {
        List<Producto> lista = new ArrayList();
        try {
            ps=CON.conectar().prepareStatement("select p.id,p.nombre,p.descripcion,p.categoriaid,c.nombre as categoria_nombre from productos p inner join categorias c on p.categoriaid=c.id where p.nombre Like ? order by p.id asc limit ?,?");
            ps.setString(1,"%"+texto+"%");
            ps.setInt(2, (numPagina-1)*totalPorPagina);
            ps.setInt(3, totalPorPagina);
            rs=ps.executeQuery();
            while(rs.next()){
                lista.add(new Producto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return lista;
    }

    @Override
    public boolean insertar(Producto obj) {
        boolean resp=false;
        try {
            ps = CON.conectar().prepareStatement("INSERT INTO productos (nombre,categoriaid,descripcion) VALUES (?,?,?)");
            ps.setString(1, obj.getNombre());
            ps.setInt(2, obj.getCategoriaId());
            ps.setString(3, obj.getDescripcion());
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
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
            ps = CON.conectar().prepareStatement("DELETE FROM productos WHERE id=?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public int total() {
        int total=0;
        try {
            ps=CON.conectar().prepareStatement("select count(id) from productos");
            rs=ps.executeQuery();
            while(rs.next()){
                total=rs.getInt("COUNT(id)");
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return total;
    }

    @Override
    public int existe(String texto) {
        int id=0;
        try {
            ps=CON.conectar().prepareStatement("select id from productos where nombre=?");
            ps.setString(1, texto);
            rs=ps.executeQuery();
            while(rs.next()){
                id=rs.getInt(1);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return id;
    }
    
    @Override
	public Producto obtener(int id) {
		Producto reg=new Producto();
        try {
            ps=CON.conectar().prepareStatement("SELECT id,nombre,descripcion,categoriaid FROM productos where id=?");
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                reg.setId(rs.getInt(1));
                reg.setNombre(rs.getString(2));
                reg.setDescripcion(rs.getString(3));
                reg.setCategoriaId(rs.getInt(4));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps=null;
            CON.desconectar();
        }
        return reg;
	}
    
    @Override
    public List<Categoria> selectCategorias() {
        List<Categoria> lista = new ArrayList();
        try {
            ps=CON.conectar().prepareStatement("SELECT id, nombre FROM categorias");
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
}
