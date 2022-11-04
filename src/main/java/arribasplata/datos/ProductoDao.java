package arribasplata.datos;

import java.util.List;

import arribasplata.dominio.Categoria;

public interface ProductoDao<T> {
    public List<T> listar(String texto,int totalPorPagina,int numPagina);
    public boolean insertar(T obj);
    public boolean editar(T obj);
    public boolean eliminar(int id);
    public int total();
    public int existe(String texto);
    public T obtener(int id);
    public List<Categoria> selectCategorias();
}
