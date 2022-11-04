package arribasplata.dominio;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String password;
    private int rolId;
    private String rolNombre;
    private boolean activo;

    

    public Usuario() {
    }

    public Usuario(int id, String nombre, String email, String password, int rolId, String rolNombre) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rolId = rolId;
        this.rolNombre = rolNombre;
    }

    public Usuario(int id, String email, String rolNombre, boolean activo) {
        this.id = id;
        this.email = email;
        this.rolNombre = rolNombre;
        this.activo = activo;
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
