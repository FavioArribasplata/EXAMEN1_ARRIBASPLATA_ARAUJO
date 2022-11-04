package arribasplata.datos;

import java.util.List;

public interface CategoriaDao<T> {
    public List<T> listar(String texto);
    public boolean insertar(T obj);
    public boolean editar(T obj);
    public boolean eliminar(int id);
    public T obtener(int id);
}
