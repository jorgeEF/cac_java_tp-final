package controller;

import com.google.gson.Gson;
import modelo.Usuario;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/GetUsuarioServlet")
public class GetUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (usuario != null) {
            response.getWriter().write(new Gson().toJson(usuario));
        } else {
            response.getWriter().write("null");
        }
    }
}
