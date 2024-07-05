/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.PublicacionDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Publicacion;
import com.google.gson.Gson;


@WebServlet("/publicaciones")
    public class PublicacionesServlet extends HttpServlet {

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // Obtener las publicaciones desde la base de datos
            int pagina = 1; // Página por defecto
            int registrosPorPagina = 3; // Cantidad de publicaciones por página
            if (request.getParameter("pagina") != null) {
                pagina = Integer.parseInt(request.getParameter("pagina"));
            }

            PublicacionDAO publicacionDAO = new PublicacionDAO();
            List<Publicacion> publicaciones = publicacionDAO.obtenerPublicacionesPaginadas(pagina, registrosPorPagina);

            // Convertir las publicaciones a JSON
            Gson gson = new Gson();
            String jsonPublicaciones = gson.toJson(publicaciones);

            // Configurar la respuesta
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonPublicaciones);
        }
    }
