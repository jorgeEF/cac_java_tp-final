package controller;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.UsuarioDAO;
import modelo.Usuario;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros de la solicitud
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String password = request.getParameter("password");        

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(password);        

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean registroExitoso = usuarioDAO.crearUsuario(usuario);
        if (registroExitoso) {
            response.sendRedirect("pages/login.html?exito=true");
        } else {
            response.sendRedirect("pages/registrarse.html?error=true");
        }
    }
}
