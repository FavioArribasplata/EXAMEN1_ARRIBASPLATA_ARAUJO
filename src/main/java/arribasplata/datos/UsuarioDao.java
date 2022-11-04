package arribasplata.datos;

import java.util.List;

public interface UsuarioDao<T> {
    public List<T> listar(String texto);
    public boolean insertar(T obj);
    public boolean actualizar(T obj);
    public boolean desactivar(int id);
    public boolean activar(int id);
    public T login(String email, String clave);
}
