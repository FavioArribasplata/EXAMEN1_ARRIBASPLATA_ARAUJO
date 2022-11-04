package arribasplata.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import datos.impl.ProductoDaoImpl;
import dominio.Categoria;
import dominio.Producto;

/**
 * Servlet implementation class ProductoController
 */
@WebServlet(name = "AnnotatedServlet", description = "A sample annotated servlet", urlPatterns = { "/Producto" })
public class ProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductoDaoImpl datos;
	Producto obj;
	int totalPorPagina;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		datos = new ProductoDaoImpl();
		obj = new Producto();
		totalPorPagina=10;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion=request.getParameter("accion");
		if(accion == null) {
			accion="list";
		}

		switch (accion) {
			case "create": {
				create(request,response);
				break;
			}
			case "edit": {				
				edit(request,response);
				break;
			}
			case "store": {				
				store(request,response);
				break;
			}
			case "update": {				
				update(request,response);
				break;
			}
			case "delete": {
				delete(request,response);
				break;
			}	
			case "list": {
				list(request,response,null);
				break;
			}	
			default: {
				list(request,response,"");
				break;
			}
		}
	}

	private void create(HttpServletRequest request, HttpServletResponse response) {
		obj = new Producto();
		obj.setNombre("");
		obj.setDescripcion("");
		obj.setCategoriaId(0);
		List<Categoria> selectCategorias=datos.selectCategorias();
		try {
			request.setAttribute("reg", obj);
			request.setAttribute("selectCategorias", selectCategorias);
			request.getRequestDispatcher("producto/form.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		obj = datos.obtener(Integer.parseInt(id));
		List<Categoria> selectCategorias=datos.selectCategorias();
		try {
			request.setAttribute("reg", obj);
			request.setAttribute("selectCategorias", selectCategorias);
			request.getRequestDispatcher("producto/form.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void store(HttpServletRequest request, HttpServletResponse response) {
		String nombre = request.getParameter("nombre");
		String descripcion=request.getParameter("descripcion");
		String categoriaId=request.getParameter("categoriaid");
		String mensaje = "Error al insertar el registro";
		boolean resp;
		try {
			obj.setNombre(nombre);
			obj.setDescripcion(descripcion);
			obj.setCategoriaId(Integer.parseInt(categoriaId));
			resp = datos.insertar(obj);
			if (resp) {
				mensaje = "Registro insertado correctamente";
			}
			list(request, response, mensaje);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String nombre = request.getParameter("nombre");
		String descripcion=request.getParameter("descripcion");
		String categoriaId=request.getParameter("categoriaid");
		String mensaje = "Error al actualizar el registro";
		boolean resp;
		try {
			obj.setId(id);
			obj.setNombre(nombre);
			obj.setDescripcion(descripcion);
			obj.setCategoriaId(Integer.parseInt(categoriaId));
			resp = datos.actualizar(obj);
			if (resp) {
				mensaje = "Registro actualizado correctamente";
			}
			list(request, response, mensaje);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		boolean resp;

		String mensaje = "Error al eliminar el registro";
		try {
			resp = datos.eliminar(id);
			if (resp) {
				mensaje = "Registro eliminado correctamente";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		list(request, response, mensaje);
	}

	private void list(HttpServletRequest request, HttpServletResponse response, String mensaje) {
		String texto = request.getParameter("texto");
		String paginaRequest=request.getParameter("pagina");
		int pagina;
		int paginas=paginar();
		boolean inicio=false;
		boolean fin=false;
		if (texto == null) {
			texto = "";
		}
		if(paginaRequest==null) {
			paginaRequest="1";
		}
		pagina=Integer.parseInt(paginaRequest);
		if(pagina==1) {
			inicio=true;
		}
		if(pagina>=paginas) {
			fin=true;
		}
		try {
			List<Producto> lista = datos.listar(texto,totalPorPagina,pagina);
			request.setAttribute("lista", lista);
			request.setAttribute("texto", texto);
			request.setAttribute("mensaje", mensaje);
			request.setAttribute("pagina", pagina);
			request.setAttribute("inicio", inicio);
			request.setAttribute("fin", fin);
			request.getRequestDispatcher("producto/index.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int paginar() {
		int totalRegistros=datos.total();
		int totalPaginas;
		totalPaginas=(int)(Math.ceil((double)totalRegistros/totalPorPagina));
        if(totalPaginas==0){
            totalPaginas=1;
        }
        return totalPaginas;
	}

}
