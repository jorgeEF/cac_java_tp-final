/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Publicacion;
import dao.PublicacionDAO;
import java.sql.Date;


/**
 *
 * @author Jef
 */
public class PublicacionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros de la solicitud
        String titulo = request.getParameter("titulo");
        String contenido = request.getParameter("contenido");
        String img_id = request.getParameter("img_id");
        String fecha = request.getParameter("fecha");
        String creator_id = request.getParameter("creator_id");
        

        Publicacion post = new Publicacion();
        post.setTitulo(titulo);
        post.setContenido(contenido);
        post.setImg_id(Integer.parseInt(img_id));
        //convierte una cadena de texto a un objeto Date
        //proviene del paquete java.sql
        post.setFecha(Date.valueOf(fecha));
        post.setCreator_id(Integer.parseInt(creator_id));
        

        PublicacionDAO publicacionDAO = new PublicacionDAO();
        boolean publicacionExitosa = publicacionDAO.guardarPublicacion(post);
        if (publicacionExitosa) {
            response.sendRedirect("pages/publicar.html?exito=true");
        } else {
            response.sendRedirect("pages/publicar.html?error=true");
        }
    }
}