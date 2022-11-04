package arribasplata.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import datos.impl.CategoriaDaoImpl;
import dominio.Categoria;
/**
 * Servlet implementation class CategoriaController
 */

@WebServlet(
	    name = "AnnotatedServlet",
	    description = "A sample annotated servlet",
	    urlPatterns = {"/Categoria"}
	)
public class CategoriaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoriaDaoImpl datos;
	Categoria obj;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoriaController() {
        super();
        
    }
    
    public void init() {
    	datos=new CategoriaDaoImpl();
    	obj=new Categoria();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("CategoriaController");
		
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
		obj=new Categoria();
		obj.setNombre("");
		try {
			request.setAttribute("reg",obj);
			request.getRequestDispatcher("categoria/form.jsp").forward(request,response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void edit(HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		obj=datos.obtener(Integer.parseInt(id));	
		try {
			request.setAttribute("reg",obj);
			request.getRequestDispatcher("categoria/form.jsp").forward(request,response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void store(HttpServletRequest request, HttpServletResponse response) {
		String nombre=request.getParameter("nombre");
		String mensaje="Error al insertar el registro";
		boolean resp;
		try {
			obj.setNombre(nombre);
			resp=datos.insertar(obj);
			if(resp) {
				mensaje="Registro insertado correctamente";
			}
			list(request,response,mensaje);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void update(HttpServletRequest request, HttpServletResponse response) {
		int id=Integer.parseInt(request.getParameter("id"));
		String nombre=request.getParameter("nombre");
		String mensaje="Error al actualizar el registro";
		boolean resp;
		try {
			obj.setId(id);
			obj.setNombre(nombre);
			resp=datos.actualizar(obj);
			if(resp) {
				mensaje="Registro actualizado correctamente";
			}
			list(request,response,mensaje);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		boolean resp;
		
		String mensaje="Error al eliminar el registro";
		try {
			resp=datos.eliminar(id);
			if(resp) {
				mensaje="Registro eliminado correctamente";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		list(request,response,mensaje);
	}

	private void list(HttpServletRequest request, HttpServletResponse response,String mensaje) {
		String texto=request.getParameter("texto");
		if(texto== null){
			texto="";
		}		
		try {
			List<Categoria> lista=datos.listar(texto);
			request.setAttribute("lista",lista);
			request.setAttribute("texto",texto);
			request.setAttribute("mensaje",mensaje);
			request.getRequestDispatcher("categoria/index.jsp").forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
}
